# File: Ball_Trajectory.py
# Description: This file will calculate the necessary launch angle
#                for shooting FRC balls into marked holes

# importing libraries
from math import pi, radians, degrees, sin, cos, atan, sqrt, sinh, cosh, asinh, tan
import numpy as np
from scipy.integrate import quadrature
from scipy.optimize import newton
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
a_ball = pi * (d_ball / 2) ** 2  # [m^2] Max Ball Cross-Sectional Area (Projected Area)
vis_air = 1.562e-5  # [m^2/s] @ 25C Air Kinematic Viscosity
rho_air = 1.184  # [kg/m^3] @ 25C Air Density
c_d = 0.5  # Sphere Drag Coefficient
mu = 0.5 * c_d * rho_air * a_ball / m_ball  # [m^-1] Ball Drag Acceleration Coefficient
invalid = "Error: Invalid Input"


# static function declarations
def lam(Q, Zeta):
    return Zeta - (Q + 0.5 * sinh(2 * Q))


def x_func(Q, Zeta):
    return cosh(Q) / lam(Q, Zeta)


def y_func(Q, Zeta):
    return sinh(2 * Q) / lam(Q, Zeta)


def x_drag(Q_initial, Q, Zeta):
    return -1 * quadrature(x_func, Q_initial, Q, Zeta, vec_func=False)[0] / mu


def y_drag(Q_initial, Q, Zeta):
    return -1 * quadrature(y_func, Q_initial, Q, Zeta, vec_func=False)[0] / (2 * mu)


# class declarations
class Projectile:
    def __init__(self, initial_height, initial_velocity, data_points):
        # variables necessary for gravity model
        self.h_0 = initial_height
        self.v_0 = initial_velocity
        self.pts = data_points
        self.x_g = np.empty(self.pts)
        self.y_g = np.empty([2, self.pts])
        self.h = None
        self.dist_ft_g = None
        self.dist_m_g = None
        self.angle_0_g = np.empty(2)
        # variables necessary for gravity & drag model
        self.v_xf_g = np.empty(2)
        self.v_yf_g = np.empty(2)
        self.t_g = np.empty(2)
        self.angle_f_g = np.empty(2)
        self.v_x0 = np.empty(2)
        self.v_y0 = np.empty(2)
        self.Q_0 = np.empty(2)
        self.zeta = np.empty(2)
        self.Q_f = np.empty(2)
        self.x_f_d = np.empty(2)

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
                print("\nMax distance for selected target is {0:.2f} [ft]".format(max_dist_g))
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

        # launch_angle = atan((a +- sqrt(b - c)) / d)
        # launch_angle = atan((a +- e)/d)

        a = self.v_0 ** 2
        b = a ** 2
        c = g * (g * self.dist_m_g ** 2 + 2 * self.h * a)
        d = g * self.dist_m_g
        e = sqrt(b - c)
        self.angle_0_g[0] = atan((a + e) / d)  # [rad]
        self.angle_0_g[1] = atan((a - e) / d)  # [rad]
        # print(self.angle_0_g[1])

    def ball_path_g(self):
        # function:
        # returns:

        # y = c1 * x + c2 * x^2
        # c1 = tan(launch_angle)
        # c2 = g / (2 * v_initial^2 * cos^2(launch_angle))

        c1 = np.empty(2)
        c2 = np.empty(2)
        self.x_g = np.linspace(0, self.dist_m_g, self.pts)
        for j in range(2):
            c1[j] = tan(self.angle_0_g[j])
            c2[j] = g / (2 * self.v_0 ** 2 * cos(self.angle_0_g[j]) ** 2)
            self.v_xf_g[j] = self.v_0 * cos(self.angle_0_g[j])
            self.t_g[j] = self.dist_m_g / (self.v_0 * cos(self.angle_0_g[j]))
            self.v_yf_g[j] = self.v_0 * sin(self.angle_0_g[j]) - g * self.t_g[j]
            self.angle_f_g[j] = tan(self.v_yf_g[j] / self.v_xf_g[j])
            for i in range(self.pts):
                self.y_g[j, i] = c1[j] * self.x_g[i] - c2[j] * self.x_g[i] ** 2
        return self.x_g, self.y_g[0, :], self.y_g[1, :]

    # start of gravity and drag methods
    def drag_cte(self, initial_angle_guesses):
        # function:
        # input:

        for i in range(np.size(initial_angle_guesses)):
            self.Q_0[i] = asinh(tan(initial_angle_guesses[i]))
            self.v_x0[i] = self.v_0 * cos(initial_angle_guesses[i])
            self.v_y0[i] = self.v_0 * sin(initial_angle_guesses[i])
            self.zeta[i] = g / (mu * self.v_x0[i] ** 2) + self.Q_0[i] + 0.5 * sinh(2 * self.Q_0[i])
        # print(self.Q_0[1], self.v_x0[1], self.v_y0[1], self.zeta[1])

    def q_final(self, final_angle_guesses):
        # function:
        # input:

        for i in range(np.size(final_angle_guesses)):
            self.Q_f[i] = asinh(tan(final_angle_guesses[i]))
        # print(self.Q_f[1])


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
    angle1_0_g = trajectory.angle_0_g[0]
    angle2_0_g = trajectory.angle_0_g[1]
    [x_path_m_g, y1_path_m_g, y2_path_m_g] = trajectory.ball_path_g()
    x_path_ft_g = x_path_m_g * m2ft
    y1_path_ft_g = y1_path_m_g * m2ft
    y2_path_ft_g = y2_path_m_g * m2ft

    # start of ball trajectory calculations with gravity & drag acting
    # initial angle guesses
    trajectory.drag_cte(trajectory.angle_0_g)
    trajectory.q_final(trajectory.angle_f_g)
    # final angle guesses
    angle1_d = atan(sinh(trajectory.Q_f[0]))
    angle2_d = atan(sinh(trajectory.Q_f[1]))
    # linearly spaced array of flight angle substitute variable
    Q1 = np.arcsinh(np.tan(np.linspace(angle1_0_g, angle1_d, data_pts)))
    Q2 = np.arcsinh(np.tan(np.linspace(angle2_0_g, angle2_d, data_pts)))
    # vectorized x & y position functions
    x_vec = np.vectorize(x_drag)
    y_vec = np.vectorize(y_drag)
    # reacquiring drag model constants
    zeta1 = trajectory.zeta[0]
    zeta2 = trajectory.zeta[1]
    Q1_0 = trajectory.Q_0[0]
    Q2_0 = trajectory.Q_0[1]
    # calculating ball trajectories with drag & gravity acting
    x1_path_ft_d = x_vec(Q1_0, Q1, zeta1) * m2ft
    x2_path_ft_d = x_vec(Q2_0, Q2, zeta2) * m2ft
    y1_path_ft_d = y_vec(Q1_0, Q1, zeta1) * m2ft
    y2_path_ft_d = y_vec(Q2_0, Q2, zeta2) * m2ft

    # plotting trajectories
    fig, ax = plt.subplots()
    legend_str = "Launch Angle: {:.2f}$^\circ$"
    line1 = ax.plot(
        x_path_ft_g, y1_path_ft_g, "b:", label=legend_str
        .format(degrees(angle1_0_g)))
    line2 = ax.plot(
        x_path_ft_g, y2_path_ft_g, "r:", label=legend_str
        .format(degrees(angle2_0_g)))
    line3 = ax.plot(
        x1_path_ft_d, y1_path_ft_d, "b", label=legend_str
        .format(degrees(angle1_0_g)))
    line4 = ax.plot(
        x2_path_ft_d, y2_path_ft_d, "r", label=legend_str
        .format(degrees(angle2_0_g)))
    ax.legend()
    ax.set_title("Ball Trajectory "
                 "(Dotted=Gravity, Solid=Drag+Gravity)")
    ax.set_xlabel("x (ft)")
    ax.set_ylabel("y (ft)")
    plt.show()
