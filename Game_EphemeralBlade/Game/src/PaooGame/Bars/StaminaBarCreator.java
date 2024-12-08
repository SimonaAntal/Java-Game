package PaooGame.Bars;

public class StaminaBarCreator extends BarFactory{
    @Override
    public Bar CreateBar() {
        return new PlayerStaminaBar();
    }
}
