package PaooGame.Bars;

public class HealthBarCreator extends BarFactory{
    @Override
    public Bar CreateBar() {
        return new PlayerHealthBar();
    }
}
