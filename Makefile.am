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

# Tests {{{ #
TICTACTOE_TESTS = \
	test/game_test

TESTS = $(TICTACTOE_TESTS)
check_PROGRAMS = $(TICTACTOE_TESTS)

test_game_test_SOURCES = \
	test/game_test.cpp \
	tictactoe/game/game.cpp
# }}} Tests #
