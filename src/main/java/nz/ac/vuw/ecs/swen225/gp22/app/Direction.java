package nz.ac.vuw.ecs.swen225.gp22.app;

enum Direction {
    None(0d, 0d) {},
    Up(0d, -1d) {
        @Override
        Direction right() {
            return UpRight;
        }

        @Override
        Direction left() {
            return UpLeft;
        }

        @Override
        Direction unUp() {
            return None;
        }
    },
    UpRight(+0.7071d, -0.7071d) {
        @Override
        Direction up() {
            return this;
        }

        @Override
        Direction right() {
            return this;
        }

        @Override
        Direction unUp() {
            return Right;
        }

        @Override
        Direction unRight() {
            return Up;
        }
    },
    Right(+1d, 0d) {
        @Override
        Direction up() {
            return UpRight;
        }

        @Override
        Direction down() {
            return DownRight;
        }

        @Override
        Direction unRight() {
            return None;
        }
    },
    DownRight(+0.7071d, +0.7071d) {
        @Override
        Direction right() {
            return this;
        }

        @Override
        Direction down() {
            return this;
        }

        @Override
        Direction unDown() {
            return Right;
        }

        @Override
        Direction unRight() {
            return Down;
        }
    },
    Down(0d, +1d) {
        @Override
        Direction right() {
            return DownRight;
        }

        @Override
        Direction left() {
            return DownLeft;
        }

        @Override
        Direction unDown() {
            return None;
        }
    },
    DownLeft(-0.7071d, +0.7071d) {
        @Override
        Direction down() {
            return this;
        }

        @Override
        Direction left() {
            return this;
        }

        @Override
        Direction unDown() {
            return Left;
        }

        @Override
        Direction unLeft() {
            return Down;
        }
    },
    Left(-1d, 0d) {
        @Override
        Direction up() {
            return UpLeft;
        }

        @Override
        Direction down() {
            return DownLeft;
        }

        @Override
        Direction unLeft() {
            return None;
        }
    },
    UpLeft(-0.7071d, -0.7071d) {
        @Override
        Direction up() {
            return this;
        }

        @Override
        Direction left() {
            return this;
        }

        @Override
        Direction unUp() {
            return Left;
        }

        @Override
        Direction unLeft() {
            return Up;
        }
    };
//    public final Point arrow;

    Direction(double x, double y) {
        //arrow = new Point(x, y);
    }

    Direction up() {
        return Up;
    }

    Direction right() {
        return Right;
    }

    Direction down() {
        return Down;
    }

    Direction left() {
        return Left;
    }

    Direction unUp() {
        return this;
    }

    Direction unRight() {
        return this;
    }

    Direction unDown() {
        return this;
    }

    Direction unLeft() {
        return this;
    }

//    Point arrow(Double speed) {
//        return arrow.times(speed, speed);
//    }
}