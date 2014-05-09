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

#include <tictactoe/game/tictactoe.hpp>

#include <cstdlib>

#include <boost/random/mersenne_twister.hpp>
#include <boost/random/uniform_int_distribution.hpp>

namespace tictactoe {

TicTacToe::TicTacToe() : kBoardWidth(3), kBoardHeight(3), board_(NULL),
    kWinnerO(-3), kWinnerX(3) {}

void TicTacToe::set_mark(const unsigned i, const unsigned j) {
    if (invalid_) {
        return;
    }

    if (game_done_) {
        FireGameWinner(*last_winner_);
        return;
    }

    if ((i >= kBoardHeight) || (j >= kBoardWidth)) {
        FireInvalidPosition(i, j);
        return;
    }

    const Player::Mark board_mark = mark(i, j);
    if (board_mark == Player::kNoMark) {
        board_[index_mark(i, j)] = current_player_->mark();
        mark_count_++;

        FireMarked(*current_player_, i, j);

        Player::Mark winner_mark = CheckVictory();
        if (winner_mark != Player::kNoMark) {
            const Player& winner = Player::ByMark(player_1_, player_2_,
                winner_mark);
            last_winner_ = current_player_;
            FireGameWinner(winner);
            return;
        } else {
            if (mark_count_ == kBoardWidth * kBoardHeight) {
                FireGameDraw();
                return;
            }
        }

        ChangePlayer();
    } else {
        FirePositionIsNotEmpty(i, j);
    }
}

void TicTacToe::Initialize() {
    board_ = new Player::Mark[kBoardWidth * kBoardHeight];
    game_done_ = false;
    mark_count_ = 0;
    invalid_ = false;

    CleanBoard();
    CheckPlayerConfiguration();

    if (invalid_) {
        return;
    }

    boost::random::mt19937 generator(time(0));
    boost::random::uniform_int_distribution<> distribution(0, 1);
    if (distribution(generator) == 0) {
        current_player_ = &player_1_;
    } else {
        current_player_ = &player_2_;
    }

    last_winner_ = current_player_;
    current_mark_ = current_player_->mark();

    FireCurrentPlayerChanged(*current_player_);
}

void TicTacToe::Finalize() {
    delete [] board_;
    board_ = NULL;
}

void TicTacToe::AddListener(TicTacToeListener* listener) {
    if (listener != NULL) {
        listeners_.push_back(listener);
    }
}

void TicTacToe::RemoveListener(TicTacToeListener* listener) {
    if (listener != NULL) {
        listeners_.remove(listener);
    }
}

void TicTacToe::CleanBoard() {
    const unsigned size = kBoardWidth * kBoardHeight;
    for (unsigned i = 0; i < size; ++i) {
        board_[i] = Player::kNoMark;
    }
}

void TicTacToe::CheckPlayerConfiguration() {
    if ((player_1_.name() == "") || (player_2_.name() == "")) {
        FireInvalidConfiguration(player_1_, player_2_);
        invalid_ = true;
    } else if (player_1_.name() == player_2_.name()) {
        FireInvalidConfiguration(player_1_, player_2_);
        invalid_ = true;
    } else if ((player_1_.mark() == Player::kNoMark) ||
            (player_2_.mark() == Player::kNoMark)) {

        FireInvalidConfiguration(player_1_, player_2_);
        invalid_ = true;
    } else if (player_1_.mark() == player_2_.mark()) {
        FireInvalidConfiguration(player_1_, player_2_);
        invalid_ = true;
    }
}

void TicTacToe::ChangePlayer() {
    if (current_mark_ == player_1_.mark()) {
        current_player_ = &player_2_;
    } else {
        current_player_ = &player_1_;
    }

    current_mark_ = current_player_->mark();
    FireCurrentPlayerChanged(*current_player_);
}

const Player::Mark TicTacToe::CheckVictory() const {
    int horizontals[] = { 0, 0, 0 };
    int verticals[] = { 0, 0, 0 };
    int diagonals[] = { 0, 0 };

    Player::Mark board_mark = Player::kNoMark;
    for (unsigned i = 0; i < kBoardHeight; ++i) {
        for (unsigned j = 0; j < kBoardWidth; ++j) {
            board_mark = mark(i, j);

            if (board_mark == Player::kMarkO) {
                horizontals[i]--;
                verticals[j]--;

                if (i == j) {
                    diagonals[0]--;
                }
                if (((kBoardWidth - 1) - j - i) == 0) {
                    diagonals[1]--;
                }
            } else if (board_mark == Player::kMarkX) {
                horizontals[i]++;
                verticals[j]++;

                if (i == j) {
                    diagonals[0]++;
                }
                if (((kBoardWidth - 1) - j - i) == 0) {
                    diagonals[1]++;
                }
            }
        }
    }

    for (unsigned i = 0; i < kBoardWidth; ++i) {
        if (i < (kBoardWidth - 1)) {
            if (diagonals[i] == kWinnerO) {
                return Player::kMarkO;
            } else if (diagonals[i] == kWinnerX) {
                return Player::kMarkX;
            }
        }

        if (horizontals[i] == kWinnerO) {
            return Player::kMarkO;
        } else if (horizontals[i] == kWinnerX) {
            return Player::kMarkX;
        }

        if (verticals[i] == kWinnerO) {
            return Player::kMarkO;
        } else if (verticals[i] == kWinnerX) {
            return Player::kMarkX;
        }
    }

    return Player::kNoMark;
}

void TicTacToe::FireMarked(const Player& player, const unsigned i,
                           const unsigned j) {

    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->Marked(player, i, j);
    }
}

void TicTacToe::FireGameWinner(const Player& player) {
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->GameWinner(player);
    }
}

void TicTacToe::FireGameDraw() {
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->GameDraw();
    }
}

void TicTacToe::FireCurrentPlayerChanged(const Player& player) {
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->CurrentPlayerChanged(player);
    }
}

void TicTacToe::FireInvalidPosition(const unsigned i, const unsigned j) {
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->InvalidPosition(i, j);
    }
}

void TicTacToe::FirePositionIsNotEmpty(const unsigned i, const unsigned j) {
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->PositionIsNotEmpty(i, j);
    }
}


void TicTacToe::FireInvalidConfiguration(const Player& player_1,
                                         const Player& player_2) {

    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->InvalidConfiguration(player_1_, player_2_);
    }
}

} /* namespace tictactoe */

