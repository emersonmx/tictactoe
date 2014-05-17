#include <iostream>
#include <tictactoe/game/tictactoe.hpp>
#include <tictactoe/game/tictactoe_event.hpp>

using namespace std;
using namespace tictactoe;

class TestListener : public TicTacToeListener {
    public:
        static void DrawBoard(const TicTacToe* source) {
            cout << "+---+---+---+\n";
            for (unsigned i = 0; i < TicTacToe::kBoardHeight; ++i) {
                cout << "| ";
                for (unsigned j = 0; j < TicTacToe::kBoardWidth; ++j) {
                    const Player::Mark mark = source->mark(i, j);
                    if (mark == Player::kNoMark) {
                        cout << " ";
                    } else if (mark == Player::kMarkO) {
                        cout << "O";
                    } else if (mark == Player::kMarkX) {
                        cout << "X";
                    }
                    cout << " | ";
                }
                cout << "\n+---+---+---+\n";
            }
        }

        virtual void GameStarted(const TicTacToeEvent& event) {
            const TicTacToe* source = event.source();

            DrawBoard(source);

            cout << "Game start!\n\n";
        }

        virtual void GameOver(const TicTacToeEvent& event) {
            cout << "The game is over!\n";
        }

        virtual void Marked(const TicTacToeEvent& event) {
            const TicTacToe* source = event.source();
            const Player* player = source->current_player();

            DrawBoard(source);

            cout << "Player \"" << player->name() << "\" mark "
                 << ((player->mark() == Player::kMarkO) ? "O" : "X") << endl;
        }

        virtual void GameWinner(const TicTacToeEvent& event) {
            const TicTacToe* source = event.source();
            const Player* player = source->current_player();

            cout << "Winner \"" << player->name() << "\" Mark "
                 << ((player->mark() == Player::kMarkO) ? "O" : "X") << endl;
        }

        virtual void GameDraw(const TicTacToeEvent& event) {
            cout << "\nGame draw!\n";
        }

        virtual void CurrentPlayerChanged(const TicTacToeEvent& event) {
            const TicTacToe* source = event.source();
            const Player* player = source->current_player();

            cout << "\nCurrent player is \"" << player->name() << "\" with mark "
                 << ((player->mark() == Player::kMarkO) ? "O" : "X") << endl;
        }

        virtual void InvalidPosition(const TicTacToeEvent& event) {
            cout << "Invalid Position\n";
        }

        virtual void PositionIsNotEmpty(const TicTacToeEvent& event) {
            cout << "Position is not empty\n";
        }

        virtual void InvalidConfiguration(const TicTacToeEvent& event) {
            cout << "Invalid configuration!\n";
        }
};

int main() {
    TestListener t;
    TicTacToe game;
    game.set_player_1(Player("Player 1", Player::kMarkO));
    game.set_player_2(Player("Player 2", Player::kMarkX));
    game.AddListener(&t);

    game.Initialize();

    game.set_mark(0, 0);
    game.set_mark(1, 0);
    game.set_mark(2, 0);

    game.set_mark(0, 2);
    game.set_mark(1, 2);
    game.set_mark(2, 2);

    game.set_mark(0, 1);
    game.set_mark(1, 1);
    game.set_mark(2, 1);

    game.Restart();

    game.set_mark(0, 0);
    game.set_mark(1, 0);
    game.set_mark(2, 0);

    game.set_mark(0, 1);
    game.set_mark(1, 1);
    game.set_mark(2, 1);

    game.set_mark(0, 2);

    game.Restart();

    game.set_mark(0, 0);
    game.set_mark(0, 2);
    game.set_mark(1, 0);
    game.set_mark(1, 2);
    game.set_mark(2, 0);

    game.Finalize();

    return 0;
}

