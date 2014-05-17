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

#ifndef TICTACTOE_GAME_GAME_EVENT_HPP_
#define TICTACTOE_GAME_GAME_EVENT_HPP_

namespace tictactoe {

class Player;

class GameEvent {
    public:
        GameEvent(const Game* source) : source_(source) {}

        inline const Game* source() const { return source_; }

    private:
        const Game* source_;
};

} /* namespace tictactoe */
#endif /* TICTACTOE_GAME_GAME_EVENT_HPP_ */

