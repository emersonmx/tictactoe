# Global configuration {{{ #
ACLOCAL_AMFLAGS = -I m4

SUBDIRS = mxgame/

AM_CPPFLAGS = \
	-Wall \
	-I${srcdir}/mxgame/

AM_CXXFLAGS = \
	-Wall

AM_LDFLAGS = \
	-Lmxgame/
# }}} Global configuration #

# Game {{{ #
bin_PROGRAMS = ttt

ttt_SOURCES = \
	tictactoe/game/main.cpp \
	tictactoe/game/application.cpp \
	tictactoe/game/game.cpp

ttt_CPPFLAGS = \
	$(GLFW_CFLAGS)

ttt_LDADD = \
	-lGL \
	-lGLU \
	$(GLFW_LIBS)
# }}} Game #

# Tests {{{ #
TICTACTOE_TESTS = \
	test/game_test

TESTS = $(TICTACTOE_TESTS)
check_PROGRAMS = $(TICTACTOE_TESTS)

test_game_test_SOURCES = \
	test/game_test.cpp \
	tictactoe/game/game.cpp
# }}} Tests #
