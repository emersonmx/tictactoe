#include <iostream>
#include <tictactoe/game/game.hpp>
#include <tictactoe/game/game_event.hpp>

using namespace std;
using namespace tictactoe;

class TestListener : public GameListener {
    public:
        static void DrawBoard(const Game* source) {
            cout << "+---+---+---+\n";
            for (unsigned i = 0; i < Game::kBoardHeight; ++i) {
                cout << "| ";
                for (unsigned j = 0; j < Game::kBoardWidth; ++j) {
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

        virtual void GameStarted(const GameEvent& event) {
            const Game* source = event.source();

            DrawBoard(source);

            cout << "Game start!\n\n";
        }

        virtual void GameOver(const GameEvent& event) {
            cout << "The game is over!\n";
        }

        virtual void Marked(const GameEvent& event) {
            const Game* source = event.source();
            const Player* player = source->current_player();

            DrawBoard(source);

            cout << "Player \"" << player->name() << "\" mark "
                 << ((player->mark() == Player::kMarkO) ? "O" : "X") << endl;
        }

        virtual void GameWinner(const GameEvent& event) {
            const Game* source = event.source();
            const Player* player = source->current_player();

            cout << "Winner \"" << player->name() << "\" Mark "
                 << ((player->mark() == Player::kMarkO) ? "O" : "X") << endl;
        }

        virtual void GameDraw(const GameEvent& event) {
            cout << "\nGame draw!\n";
        }

        virtual void CurrentPlayerChanged(const GameEvent& event) {
            const Game* source = event.source();
            const Player* player = source->current_player();

            cout << "\nCurrent player is \"" << player->name() << "\" with mark "
                 << ((player->mark() == Player::kMarkO) ? "O" : "X") << endl;
        }

        virtual void InvalidPosition(const GameEvent& event) {
            cout << "Invalid Position\n";
        }

        virtual void PositionIsNotEmpty(const GameEvent& event) {
            cout << "Position is not empty\n";
        }

        virtual void InvalidConfiguration(const GameEvent& event) {
            cout << "Invalid configuration!\n";
        }
};

int main() {
    TestListener t;
    Game game;
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

