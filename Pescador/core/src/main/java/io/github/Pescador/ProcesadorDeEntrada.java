package io.github.Pescador;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class ProcesadorDeEntrada extends InputAdapter {


    @Override
    public boolean keyDown(int keycode) {
        if(!Mundo.anzuelo.estaParado()) return true;
        switch(keycode) {
            case Input.Keys.RIGHT:
                Mundo.pescador.adelante(); break;
            case Input.Keys.LEFT:
                Mundo.pescador.atras(); break;
            case Input.Keys.DOWN:
                Mundo.pescador.para();
                Mundo.anzuelo.atras();
                break;
            default: return false;
        }
        return true;
    }



    @Override
    public boolean keyUp(int keycode) {
        if(!Mundo.anzuelo.estaParado()) {
            if(keycode==Input.Keys.DOWN)
                Mundo.anzuelo.adelante();
            return true;
        }
        switch(keycode) {
            case Input.Keys.RIGHT:
                if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
                    Mundo.pescador.atras();
                else
                    Mundo.pescador.para(); break;
            case Input.Keys.LEFT:
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                    Mundo.pescador.adelante();
                else
                    Mundo.pescador.para(); break;
            default: return false;
        }
        return true;
    }
}
