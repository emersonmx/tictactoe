/*
  Copyright (C) 2014 Emerson Max de Medeiros Silva

  This file is part of tictactoe.

  tictactoe is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  tictactoe is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with tictactoe.  If not, see <http://www.gnu.org/licenses/>.
*/

#ifndef TICTACTOE_GAME_TICTACTOE_HPP_
#define TICTACTOE_GAME_TICTACTOE_HPP_

#include <string>
#include <list>

#include <tictactoe/game/player.hpp>
#include <tictactoe/game/tictactoe_listener.hpp>

namespace tictactoe {

class TicTacToe {
    public:
        static const unsigned kBoardWidth;
        static const unsigned kBoardHeight;

        TicTacToe();

        inline const Player::Mark mark(const unsigned i,
                const unsigned j) const {

            return board_[index_mark(i, j)];
        }

        void set_mark(const unsigned i, const unsigned j);

        inline const Player::Mark* board() const { return board_; }

        inline const Player& player_1() const { return player_1_; }

        inline void set_player_1(const Player& player_1) {
            player_1_ = player_1;
        }

        inline const Player& player_2() const { return player_2_; }

        inline void set_player_2(const Player& player_2) {
            player_2_ = player_2;
        }

        inline const Player* current_player() const { return current_player_; }

        inline const Player* winner() const { return winner_; }

        void Initialize();

        void Finalize();

        void Restart();

        void AddListener(TicTacToeListener* listener);

        void RemoveListener(TicTacToeListener* listener);

    private:
        typedef std::list<TicTacToeListener*> Listeners;

        static const int kWinnerO;
        static const int kWinnerX;

        static const unsigned index_mark(const unsigned i, const unsigned j) {
            return i + j * TicTacToe::kBoardWidth;
        }

        void Reset();

        void RandomPlayer();

        void Setup();

        void CleanBoard();

        void CheckPlayerConfiguration();

        void ChangePlayer();

        const Player::Mark CheckVictory() const;

        void FireGameStarted();

        void FireGameOver();

        void FireMarked();

        void FireGameWinner();

        void FireGameDraw();

        void FireCurrentPlayerChanged();

        void FireInvalidPosition();

        void FirePositionIsNotEmpty();

        void FireInvalidConfiguration();

        Player::Mark* board_;
        unsigned mark_count_;
        bool game_done_;
        bool invalid_configuration_;

        Player player_1_;
        Player player_2_;
        Player* current_player_;
        Player* winner_;
        Player::Mark current_mark_;

        Listeners listeners_;
};

} /* namespace tictactoe */
#endif /* TICTACTOE_GAME_TICTACTOE_HPP_ */

