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

#include <tictactoe/game/player.hpp>

namespace tictactoe {

class TicTacToe {
    public:
        TicTacToe();

        inline const Player::Mark mark(const unsigned i,
                                       const unsigned j) const {

            return board_[index_mark(i, j)];
        }

        void set_mark(const unsigned i, const unsigned j);

        inline const Player& player_1() const { return player_1_; }

        inline void set_player_1(const Player& player_1) {
            player_1_ = player_1;
        }

        inline const Player& player_2() const { return player_2_; }

        inline void set_player_2(const Player& player_2) {
            player_2_ = player_2;
        }

        void Initialize();

        void Finalize();

        void SetupGame();

    private:
        inline const unsigned index_mark(const unsigned i,
                                         const unsigned j) const {

            return i + j * kBoardWidth;
        }

        void CheckPlayerConfiguration();

        void CleanBoard();

        void ChangePlayer();

        const Player::Mark CheckVictory() const;

        const unsigned kBoardWidth;
        const unsigned kBoardHeight;
        Player::Mark* board_;
        unsigned mark_count_;

        const int kWinnerO;
        const int kWinnerX;

        Player player_1_;
        Player player_2_;
        Player* current_player_;
        Player* last_winner_;
        Player::Mark current_mark_;
};

} /* namespace tictactoe */
#endif /* TICTACTOE_GAME_TICTACTOE_HPP_ */

