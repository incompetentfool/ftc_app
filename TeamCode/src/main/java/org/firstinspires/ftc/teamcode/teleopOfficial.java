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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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
public class teleopOfficial extends LinearOpMode {
    double GearRatio = 1.0;
    double GearReduction = 1.0;
    double diameter = 6.0; // inches
    double PI = 3.14159265358979333822;

    public void straferight(double power){
        robot.backleft.setPower(-1*power);
        robot.frontleft.setPower(power);
        robot.backright.setPower(power);
        robot.frontright.setPower(-1*power);
    }

    public void strafeleft(double power){
        robot.backleft.setPower(power);
        robot.frontleft.setPower(-power);
        robot.backright.setPower(-1*power);
        robot.frontright.setPower(power);
    }

    /* Declare OpMode members. */
    public MechBot robot   = new MechBot();
    @Override
    public void runOpMode() {
        //hardwaremap
        robot.init(hardwareMap);

        double left;
        double right;
        double k;
        double l;
        boolean in;
        double cascade;
        double lift;
        double d1power = 0;
        double d2power = 0;
        boolean dl;
        boolean dr;
        double sl;
        double sr;

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
            //variables

            left = gamepad1.left_stick_y;
            right =  gamepad1.right_stick_y;
            cascade = gamepad2.right_stick_y;
            lift = gamepad2.left_stick_y;
            dr = gamepad2.right_bumper;
            dl = gamepad2.left_bumper;
            sl = gamepad1.left_trigger;
            sr = gamepad1.right_trigger;

            //idk what this is?
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

            telemetry.addData("button pressed?", dl);
            //dumpers
            if(dr){
                d2power = 0.5;
            }else if(gamepad2.right_trigger > 0){
                d2power = -1*gamepad2.right_trigger;
            }else{
                d2power = 0.0;
            }

            if(dl){
                d1power = -0.5;
                telemetry.addData("if is running?", dl);
            }else if(gamepad2.left_trigger > 0){
                d1power = gamepad2.left_trigger;
                telemetry.addData("else if is running?", dl);
            }else{
                d1power = 0.0;
                telemetry.addData("else is running?", dl);
            }

            telemetry.addData("value of d1power?", d1power);
            robot.dumperleft.setPower(d1power);
            robot.dumperright.setPower(d2power);

            if(sl >0){
                strafeleft(sl);
            }

            if(sr >0){
                straferight(sr);
            }

            //robot.dumperleft.setPower(d1power);
            robot.dumperright.setPower(d2power);

            //intake
            if(gamepad2.a){
                robot.intake.setPower(0.5);
            }

            if(gamepad2.b){
                robot.intake.setPower(-0.5);
            }

            if(gamepad2.x){
                robot.intake.setPower(0.0);
            }

            //intakelift
            if(lift <0){
                robot.intakelift.setPower(-0.25*lift);
            }
            if(lift >0){
                robot.intakelift.setPower(-0.25*lift);
            }

            //drive
            // Output the safe vales to the motor drives.
            robot.frontleft.setPower(left * k * l);
            robot.backleft.setPower(left * l);
            robot.frontright.setPower(right * l);
            robot.backright.setPower(right * k * l);

            //cascadelift
            robot.linearleft.setPower(cascade);
            robot.linearright.setPower(cascade);

            // Send telemetry message to signify robot running;\
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.addData("cascade", "%.2f", cascade);
            telemetry.update();

            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
