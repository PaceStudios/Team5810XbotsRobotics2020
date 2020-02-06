# File: Ball_Trajectory_simple.py
# Description: This file will calculate the necessary launch angle
#                for shooting FRC balls into marked holes

# importing libraries
from math import pi, radians, degrees, sin, cos, atan, sqrt, sinh, cosh, asinh, tan
import numpy as np
from scipy.integrate import quadrature
from scipy.optimize import newton, fsolve
import matplotlib.pyplot as plt

# unit conversion constants
m2ft = 3.28084  # [ft/m]
ft2m = 0.3048  # [m/ft]
rpm2rads = 0.104719755  # [(rad/s)/rpm]
rads2rpm = 9.549296596425384  # [rpm/(rad/s)]
in2ft = 0.0833333  # [ft/in]
in2m = 0.0254  # [m/in]
oz2kg = 0.0283495  # [kg/oz]

# declaring universal constants
g = 9.807  # [m/s^2] Gravitational Acceleration
h_low = 0.46 + 0.25 / 2  # [m] Low Target Height
h_high = 2.49  # [m] High Target Height
m_ball = 5 * oz2kg  # [kg] Ball Mass
d_ball = 0.18  # [m] Ball Diameter
# [m^2] Max Ball Cross-Sectional Area (Projected Area)
a_ball = pi * (d_ball / 2) ** 2
vis_air = 1.562e-5  # [m^2/s] @ 25C Air Kinematic Viscosity
rho_air = 1.184  # [kg/m^3] @ 25C Air Density
c_d = 0.5  # Sphere Drag Coefficient
# [m^-1] Ball Drag Acceleration Coefficient
mu = 0.5 * c_d * rho_air * a_ball / m_ball
invalid = "Error: Invalid Input"


# static function declarations
def lam(Q, Zeta):
    # function: calculate drag substitute variable
    return Zeta - (Q + 0.5 * sinh(2 * Q))


def x_func(Q, Zeta):
    # function: x_drag integral function
    return cosh(Q) / lam(Q, Zeta)


def x_drag(Q_initial, Q, Zeta):
    # function: calculates x position of a projectile experiencing drag
    return -1 * quadrature(x_func, Q_initial, Q, Zeta, vec_func=False)[0] / mu


def y_func(Q, Zeta):
    # function: y_drag integral function
    return sinh(2 * Q) / lam(Q, Zeta)


def y_drag(Q_initial, Q, Zeta):
    # function: calculates x position of a projectile experiencing drag
    return -1 * quadrature(y_func, Q_initial, Q, Zeta, vec_func=False)[0] / (2 * mu)


# class declarations
class Projectile:
    def __init__(self, initial_height, initial_velocity, data_points):
        # variables necessary for gravity model
        self.h_0 = initial_height
        self.v_0 = initial_velocity
        self.pts = data_points
        self.x_g = np.empty(self.pts)
        self.y_g = np.empty(self.pts)
        self.h = None
        self.dist_ft_g = None
        self.dist_m_g = None
        self.angle_0_g = None
        # variables necessary for gravity & drag model
        self.v_xf_g = None
        self.v_yf_g = None
        self.t_g = None
        self.angle_f_g = None
        self.v_x0 = None
        self.v_y0 = None
        self.Q_0 = None
        self.zeta = None
        self.Q_f = None
        self.x_f_d = None

    def start(self):
        # function: asks for distance from target in [ft] and target selection [high or low]

        while True:
            try:
                target = str(
                        input("\nEnter [h] for High target or [l] for Low target: "))
            except ValueError:
                print("\n", invalid)
                continue
            if target == "h":
                self.h = h_high - self.h_0
                break
            elif target == "l":
                self.h = h_low - self.h_0
                break
            else:
                print("\n", invalid,
                        "\n[h] for High target\n[l] for Low target")

        max_dist_g = (self.v_0 / g) * \
                sqrt(self.v_0 ** 2 - 2 * self.h * g) * m2ft

        while True:
            try:
                print("\nMax distance for selected target is {0:.2f} [ft]"
                        .format(max_dist_g))
                dist_g = float(
                        input("Enter linear distance from target [ft]: "))
            except ValueError:
                print("\n", invalid)
                continue
            if dist_g > max_dist_g:
                print("\nDistance inputted is too great.")
            elif dist_g < 0:
                print("\nError: Distance cannot be negative")
            elif dist_g >= 0:
                self.dist_ft_g = dist_g
                self.dist_m_g = dist_g * ft2m
                break
            else:
                print("\n", invalid)

    def launch_angle_g(self):
        # function:

        angle = np.empty(2)
        a = self.v_0 ** 2
        b = a ** 2
        c = g * (g * self.dist_m_g ** 2 + 2 * self.h * a)
        d = g * self.dist_m_g
        e = sqrt(b - c)
        angle[0] = atan((a + e) / d)  # [rad]
        angle[1] = atan((a - e) / d)  # [rad]
        self.angle_0_g = np.min(angle)

    def ball_path_g(self):
        # function:
        # returns:

        self.x_g = np.linspace(0, self.dist_m_g, self.pts)
        c1 = tan(self.angle_0_g)
        c2 = g / (2 * self.v_0 ** 2 * cos(self.angle_0_g) ** 2)
        self.v_xf_g = self.v_0 * cos(self.angle_0_g)
        self.t_g = self.dist_m_g / (self.v_0 * cos(self.angle_0_g))
        self.v_yf_g = self.v_0 * \
                sin(self.angle_0_g) - g * self.t_g
        self.angle_f_g = tan(self.v_yf_g / self.v_xf_g)
        for i in range(self.pts):
            self.y_g[i] = c1 * self.x_g[i] - c2 * self.x_g[i] ** 2
        return self.x_g, self.y_g

    # start of gravity and drag methods
    def drag_cte(self, initial_angle_guess):
        # function:
        # input:

        self.Q_0 = asinh(tan(initial_angle_guess))
        self.v_x0 = self.v_0 * cos(initial_angle_guess)
        self.v_y0 = self.v_0 * sin(initial_angle_guess)
        self.zeta = g / (mu * self.v_x0 ** 2) + \
                self.Q_0 + 0.5 * sinh(2 * self.Q_0)

    def q_final(self, final_angle_guess):
        # function:
        # input:

        self.Q_f = asinh(tan(final_angle_guess))

if __name__ == "__main__":
    # declaring local constants
    h_initial = h_low  # [m]
    rpm_initial = 1000  # [rpm]
    w_initial = rpm_initial * rpm2rads  # [rad/s]
    r_launch_wheel = 4 * in2m  # [m] Radius
    v_initial = r_launch_wheel * w_initial  # [m/s] Velocity
    Re_max = d_ball * v_initial / vis_air  # Reynolds Number
    data_pts = 100

    # calculating ball trajectories with only gravity acting
    trajectory = Projectile(h_initial, v_initial, data_pts)
    trajectory.start()
    trajectory.launch_angle_g()
    angle_0_g = trajectory.angle_0_g
    [x_path_m_g, y_path_m_g] = trajectory.ball_path_g()
    x_path_ft_g = x_path_m_g * m2ft
    y_path_ft_g = y_path_m_g * m2ft

    # start of ball trajectory calculations with gravity & drag acting
    # target coordinates
    x_tar = trajectory.dist_m_g
    y_tar = trajectory.h
    print("\nX Target: {:.2f} ft".format(x_tar * m2ft))
    print("Y Target: {:.2f} ft".format(y_tar * m2ft))
    error_x = 1e10
    error_y = 1e10
    tol = 1
    k = trajectory.angle_f_g
    j = trajectory.angle_0_g
    while abs(error_y) > tol:
        # initial angle guess, variables derived: Q_0 & Zeta
        trajectory.drag_cte(j)
        while abs(error_x) > tol:
            # final angle guess, variable derived: Q_f
            trajectory.q_final(k)
            x = x_drag(trajectory.Q_0, trajectory.Q_f, trajectory.zeta)
            error_x = x_tar - x
            k1 = k
            k = k1 - error_x * 0.2
        y = y_drag(trajectory.Q_0, trajectory.Q_f, trajectory.zeta)
        error_y = y_tar - y
        j1 = j
        j = j1 - error_y * 0.2

    angle_0_d = j
    angle_f_d = k
    # linearly spaced array of flight angle substitute variable
    # angle_f_d = atan(sinh(trajectory.Q_f))
    Q_f = np.arcsinh(np.tan(np.linspace(angle_0_d, angle_f_d, data_pts)))
    # vectorized x & y position functions
    x_vec = np.vectorize(x_drag)
    y_vec = np.vectorize(y_drag)
    # calculating ball trajectories with drag & gravity acting
    x_path_ft_d = x_vec(trajectory.Q_0, Q_f, trajectory.zeta) * m2ft
    y_path_ft_d = y_vec(trajectory.Q_0, Q_f, trajectory.zeta) * m2ft

    # plotting trajectories
    fig, ax = plt.subplots()
    legend_str = "Launch Angle: {:.2f}$^\circ$"
    line1 = ax.plot(
            x_path_ft_g, y_path_ft_g, "b", label="Gravity " + legend_str
            .format(degrees(angle_0_g)))
    line2 = ax.plot(
            x_path_ft_d, y_path_ft_d, "r", label="Drag " + legend_str
            .format(degrees(angle_0_d)))
    ax.legend()
    ax.set_title("Ball Trajectory")
    ax.set_xlabel("x (ft)")
    ax.set_ylabel("y (ft)")
    plt.show()
