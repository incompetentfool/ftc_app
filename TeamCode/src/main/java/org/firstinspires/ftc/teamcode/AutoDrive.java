ackage org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="AutoDrive", group="Official Bot")
public class TeleopTestbot extends LinearOpMode {
    /* Declare OpMode members. */
    HardwareTestbot robot           = new HardwareTestbot();
    @Override
    public void runOpMode() {
        //controller constants
        double left;
        double right;
        double up;
        double down;

        double extra;
        double drive;
        double turn;
        double max;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Working! Hello World!");    //
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

            up = gamepad2.left_stick_y;
            down = gamepad2.right_stick_y;

//            extra = gamepad1.left_stick_y;

            // Output the safe vales to the motor drives.
            robot.leftDrive.setPower(left);
            robot.rightDrive.setPower(right);

            //these are for up and down, idk motor names at this moment
            robot..setPower(up);
            robot..setPower(down);

            // Send telemetry message to signify robot running;\
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.addData("up", "%.2f", up);
            telemetry.addData("down", "%.2f", down);
            telemetry.update();


            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}