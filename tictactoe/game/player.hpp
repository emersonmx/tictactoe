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

#ifndef TICTACTOE_GAME_PLAYER_HPP_
#define TICTACTOE_GAME_PLAYER_HPP_

#include <string>

namespace tictactoe {

class Player {
    public:
        enum Mark { kNoMark, kMarkO, kMarkX };

        static Player* ByMark(Player& player_1, Player& player_2,
                const Mark mark) {

            if (player_1.mark() == mark) {
                return &player_1;
            }

            return &player_2;
        }

        Player() : name_(""), mark_(kNoMark) {}

        Player(const std::string& name, const Mark mark)
                : name_(name), mark_(mark) {}

        inline std::string name() const { return name_; }

        inline void set_name(const std::string& name) { name_ = name; }

        inline Mark mark() const { return mark_; }

        inline void set_mark(const Mark mark) { mark_ = mark; }

    private:
        std::string name_;
        Mark mark_;
};

} /* namespace tictactoe */
#endif /* TICTACTOE_GAME_PLAYER_HPP_ */

