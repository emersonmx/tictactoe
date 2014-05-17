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

#include <tictactoe/game/game.hpp>

#include <cstdlib>

#include <boost/random/mersenne_twister.hpp>
#include <boost/random/uniform_int_distribution.hpp>

#include <tictactoe/game/game_event.hpp>

namespace tictactoe {

const unsigned Game::kBoardWidth = 3;
const unsigned Game::kBoardHeight = 3;
const int Game::kWinnerO = -3;
const int Game::kWinnerX = 3;

Game::Game() : board_(NULL) {
    Reset();
}

void Game::set_mark(const unsigned i, const unsigned j) {
    if (!invalid_configuration_) {
        if (game_done_) {
            if (mark_count_ == kBoardWidth * kBoardHeight) {
                FireGameDraw();
            } else {
                FireGameWinner();
            }
        } else {
            if ((i >= kBoardHeight) || (j >= kBoardWidth)) {
                FireInvalidPosition();
            } else {
                const Player::Mark board_mark = mark(i, j);
                if (board_mark == Player::kNoMark) {
                    board_[index_mark(i, j)] = current_player_->mark();
                    mark_count_++;

                    FireMarked();

                    Player::Mark winner_mark = CheckVictory();
                    if (winner_mark != Player::kNoMark) {
                        winner_ = Player::ByMark(player_1_, player_2_,
                            winner_mark);
                        game_done_ = true;
                        FireGameWinner();
                    } else if (mark_count_ == kBoardWidth * kBoardHeight) {
                        game_done_ = true;
                        FireGameDraw();
                    } else {
                        ChangePlayer();
                    }
                } else {
                    FirePositionIsNotEmpty();
                }
            }
        }
    }
}

void Game::Initialize() {
    board_ = new Player::Mark[kBoardWidth * kBoardHeight];
    Reset();

    RandomPlayer();

    Setup();
}

void Game::Finalize() {
    FireGameOver();

    delete [] board_;
    board_ = NULL;
}

void Game::Restart() {
    FireGameOver();

    mark_count_ = 0;
    game_done_ = false;
    invalid_configuration_ = false;

    if (winner_ != NULL) {
        current_player_ = winner_;
    } else {
        RandomPlayer();
    }

    Setup();
}

void Game::AddListener(GameListener* listener) {
    if (listener != NULL) {
        listeners_.push_back(listener);
    }
}

void Game::RemoveListener(GameListener* listener) {
    if (listener != NULL) {
        listeners_.remove(listener);
    }
}

void Game::Reset() {
    mark_count_ = 0;
    game_done_ = false;
    invalid_configuration_ = false;
    current_player_ = winner_ = NULL;
    current_mark_ = Player::kNoMark;
}

void Game::RandomPlayer() {
    boost::random::mt19937 generator(time(0));
    boost::random::uniform_int_distribution<> distribution(0, 1);
    if (distribution(generator) == 0) {
        current_player_ = &player_1_;
    } else {
        current_player_ = &player_2_;
    }
}

void Game::Setup() {
    CleanBoard();
    CheckPlayerConfiguration();

    if (!invalid_configuration_) {
        current_mark_ = current_player_->mark();
        winner_ = NULL;

        FireCurrentPlayerChanged();
        FireGameStarted();
    }
}

void Game::CleanBoard() {
    const unsigned size = kBoardWidth * kBoardHeight;
    for (unsigned i = 0; i < size; ++i) {
        board_[i] = Player::kNoMark;
    }
}

void Game::CheckPlayerConfiguration() {
    if ((player_1_.name() == "") || (player_2_.name() == "")) {
        FireInvalidConfiguration();
        invalid_configuration_ = true;
    } else if (player_1_.name() == player_2_.name()) {
        FireInvalidConfiguration();
        invalid_configuration_ = true;
    } else if ((player_1_.mark() == Player::kNoMark) ||
            (player_2_.mark() == Player::kNoMark)) {

        FireInvalidConfiguration();
        invalid_configuration_ = true;
    } else if (player_1_.mark() == player_2_.mark()) {
        FireInvalidConfiguration();
        invalid_configuration_ = true;
    }
}

void Game::ChangePlayer() {
    if (current_mark_ == player_1_.mark()) {
        current_player_ = &player_2_;
    } else {
        current_player_ = &player_1_;
    }

    current_mark_ = current_player_->mark();
    FireCurrentPlayerChanged();
}

const Player::Mark Game::CheckVictory() const {
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

void Game::FireGameStarted() {
    const GameEvent event(this);
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->GameStarted(event);
    }
}

void Game::FireGameOver() {
    const GameEvent event(this);
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->GameOver(event);
    }
}

void Game::FireMarked() {
    const GameEvent event(this);
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->Marked(event);
    }
}

void Game::FireGameWinner() {
    const GameEvent event(this);
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->GameWinner(event);
    }
}

void Game::FireGameDraw() {
    const GameEvent event(this);
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->GameDraw(event);
    }
}

void Game::FireCurrentPlayerChanged() {
    const GameEvent event(this);
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->CurrentPlayerChanged(event);
    }
}

void Game::FireInvalidPosition() {
    const GameEvent event(this);
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->InvalidPosition(event);
    }
}

void Game::FirePositionIsNotEmpty() {
    const GameEvent event(this);
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->PositionIsNotEmpty(event);
    }
}


void Game::FireInvalidConfiguration() {
    const GameEvent event(this);
    for (Listeners::iterator it = listeners_.begin();
            it != listeners_.end(); ++it) {

        (*it)->InvalidConfiguration(event);
    }
}

} /* namespace tictactoe */
