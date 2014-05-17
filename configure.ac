# Prelude
AC_INIT([tictactoe], [0.1], [emersonmx@gmail.com], [],
        [https://github.com/emersonmx/tictactoe])
AC_PREREQ(2.5)
AC_CONFIG_AUX_DIR([build-aux])
AC_CONFIG_MACRO_DIR([m4])
AM_INIT_AUTOMAKE([1.11 -Wall foreign subdir-objects silent-rules])
AM_SILENT_RULES([yes])

# Checks for programs.
# AC_PROG_CC
AC_PROG_CXX
LT_INIT

# Guess the operating system of the user.
case "$host" in
    *-*-mingw*|*-*-cygwin*)
        AC_DEFINE(PLATFORM_WINDOWS, 1, [Platform is Windows])
        ;;
    *-*-linux*)
        AC_DEFINE(PLATFORM_LINUX, 1, [Platform is Linux])
        ;;
    *-*-darwin*)
        AC_DEFINE(PLATFORM_APPLE, 1, [Platform is Apple])
        ;;
    *)
        AC_MSG_WARN([Your platform is not currently supported])
        ;;
esac

# Checks for libraries.
PKG_CHECK_MODULES([GLFW], [glfw3 >= 3.0.3], [
    AC_DEFINE([HAVE_GLFW], [1], [Define to 1 if you have the GLFW installed.])
])

# Checks for header files.

# Checks for typedefs, structures and compiler characteristics.

# Checks for library functions.

# Output files.
AC_CONFIG_HEADERS([tictactoe/config.h])
AC_CONFIG_SUBDIRS([mxgame])
AC_CONFIG_FILES([Makefile])
AC_OUTPUT
