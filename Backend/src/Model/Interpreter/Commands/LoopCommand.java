package Model.Interpreter.Commands;

public class LoopCommand extends AbstractCommand{

    public LoopCommand() {
        super(0, null);//stam
    }

    @Override
    public void execute(String[] args) {

    }

    @Override
    public int getNumOfArgs() {
        return 0;
    }

    @Override
    public void validParams(String[] args) {

    }
}