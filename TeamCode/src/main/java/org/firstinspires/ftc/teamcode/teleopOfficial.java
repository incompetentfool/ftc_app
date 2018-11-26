/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="RobotControlOfficial", group="Mech Bot")
public class TeleopOfficial extends LinearOpMode {
    double GearRatio = 1.0;
    double GearReduction = 1.0;
    double diameter = 6.0; // inches
    double PI = 3.14159265358979333822;

    //controller 1
    public DcMotor leftfront;
    public DcMotor rightfront;
    public DcMotor leftback;
    public DcMotor rightback;

    //controller 2
    public DcMotor linearleft;
    public DcMotor linearright;
    public DcMotor intake;
    public DcMotor intakelift;

    //servos
    public dumperleft;
    public dumperright;




    public

    /* Declare OpMode members. */
    MechBot robot           = new MechBot();
    @Override
    public void runOpMode() {
        //hardwaremap
        leftfront = hardwareMap.dcMotor.get("leftftont");
        rightfront = hardwareMap.dcMotor.get("rightfront");
        leftback = hardwareMap.dcMotor.get("leftback");
        rightback = hardwareMap.dcMotor.get("rightback");

        linearleft = hardwareMap.dcMotor.get("linearleft");
        linearright = hardwareMap.dcMotor.get("linearright");
        intake = hardwareMap.dcMotor.get("intake");
        intakelift = hardwareMap.dcMotor.get("intakelift");

        double left;
        double right;
        double k;
        double l;
        double intakep;
        double cascade;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        //robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            left = gamepad1.left_stick_y;
            right =  gamepad1.right_stick_y;
            intakep = gamepad2.left_stick_y;
            cascade = gamepad2.right_stick_y;




            if (gamepad1.b || gamepad1.x) {
                left = 1;
                right = 1;
                k = -1;
            } else {
                k = 1;
            }

            if (gamepad1.b) {
                l = 1;
            } else {
                l = -1;
            }

            // Output the safe vales to the motor drives.
            robot.leftFrontDrive.setPower(left * k * l);
            robot.leftRearDrive.setPower(left * l);

            robot.rightFrontDrive.setPower(right * l);
            robot.rightRearDrive.setPower(right * k * l);

            robot.

            // Send telemetry message to signify robot running;\
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.update();


            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
