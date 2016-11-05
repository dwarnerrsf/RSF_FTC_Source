package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.Gamepad;

public class RSF_MoveInput {
    private RSF_States.DPad _dpad = RSF_States.DPad.None;
    private RSF_Joysticks _joysticks = null;
    private RSF_States.MoveType _moveType = RSF_States.MoveType.None;

    public RSF_MoveInput(RSF_States.MoveType moveType) {
        _dpad = RSF_States.DPad.None;
        _joysticks = new RSF_Joysticks(0.0d, 0.0d);
        _moveType = moveType;
    }

    public RSF_MoveInput(RSF_Joysticks joysticks) {
        _dpad = RSF_States.DPad.None;
        _joysticks = joysticks;
        _moveType = RSF_States.MoveType.Joystick;
    }

    public RSF_MoveInput(RSF_States.DPad dPad) {
        _dpad = dPad;
        _joysticks = new RSF_Joysticks(0.0d, 0.0d);
        _moveType = RSF_States.MoveType.Dpad;
    }

    public RSF_States.DPad Dpad() {
        return _dpad;
    }
    public RSF_Joysticks Joystick() { return _joysticks; }
    public RSF_States.MoveType MoveType() {
        return _moveType;
    }

    public static RSF_MoveInput Check(Gamepad gamepad) {
        if (!gamepad.dpad_right && !gamepad.dpad_left && !gamepad.dpad_up && !gamepad.dpad_down) {
            double left = Clamp(-gamepad.left_stick_y);
            double right = Clamp(-gamepad.right_stick_y);

            return new RSF_MoveInput(new RSF_Joysticks(left, right));
        }
        else {
            if (gamepad.dpad_up && gamepad.dpad_left) {
                return new RSF_MoveInput(RSF_States.DPad.UpLeft);
            } else if (gamepad.dpad_up && gamepad.dpad_right) {
                return new RSF_MoveInput(RSF_States.DPad.UpRight);
            } else if (gamepad.dpad_down && gamepad.dpad_left) {
                return new RSF_MoveInput(RSF_States.DPad.DownLeft);
            } else if (gamepad.dpad_down && gamepad.dpad_right) {
                return new RSF_MoveInput(RSF_States.DPad.DownRight);
            } else if (gamepad.dpad_left) {
                return new RSF_MoveInput(RSF_States.DPad.Left);
            } else if (gamepad.dpad_right) {
                return new RSF_MoveInput(RSF_States.DPad.Right);
            } else if (gamepad.dpad_up) {
                return new RSF_MoveInput(RSF_States.DPad.Up);
            } else if (gamepad.dpad_down) {
                return new RSF_MoveInput(RSF_States.DPad.Down);
            }
            else {
                return new RSF_MoveInput(RSF_States.MoveType.None);
            }
        }
    }

    private static double Clamp(double value) {
        if (value > 1.0d) {
            return 1.0d;
        }
        else if (value < -1.0d) {
            return -1.0d;
        }
        else {
            return value;
        }
    }
}
