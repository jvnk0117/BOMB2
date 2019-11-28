package mx.itesm.videojuegos;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import javax.xml.soap.Text;

public class PantallaAcercaDe extends Pantalla {

    private final Juego juego;

    //fondosss
    private Texture texturaFondo;
    private Texture texturaBack;
    private Texture texturaAnimacion;
    private Texture texturaDesarrollo;




    //escena de menu (botones)
    private Stage escenaHUD;

    //AUDIO
    private Music musica;


    public PantallaAcercaDe (Juego juego) {
        this.juego = juego;
    }

    public PantallaAcercaDe (Juego juego, Music musica) {
        this.juego = juego;
        this.musica = musica;
    }

    @Override
    public void show() {
        manager = juego.getManager();
        cargarTexturas();
        ajustarMusica(manager);
        crearHUD();

    }

    private void ajustarMusica(AssetManager manager) {
        if (musica == null) {
            manager.load("menus/music/09 Come and Find Me - B mix.mp3", Music.class);
            manager.finishLoading();
            musica = manager.get("menus/music/09 Come and Find Me - B mix.mp3");
            musica.setLooping(true);
        }
        if (juego.playMusic == true){
            musica.play();
        } if (juego.playMusic == false){
            musica.stop();
        }
    }


    private void cargarTexturas() {
        texturaFondo = new Texture( "fondos/estatua.png");
        manager.load("menus/Nivel/prev.png",Texture.class);
        manager.load("menus/acercaDe/desarrollo.png",Texture.class);
        manager.load("menus/acercaDe/arte.png",Texture.class);

        manager.finishLoading();

        texturaBack = manager.get("menus/Nivel/prev.png");
        texturaAnimacion  = manager.get("menus/acercaDe/arte.png");
        texturaDesarrollo = manager.get("menus/acercaDe/desarrollo.png");


    }

    private void crearHUD() {
        escenaHUD = new Stage(vista);
        escenaHUD.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/prev.png")));
        TextureRegionDrawable trdDes = new TextureRegionDrawable(new TextureRegion(new Texture("menus/acercaDe/desarrollo.png")));
        TextureRegionDrawable trdAni = new TextureRegionDrawable(new TextureRegion(new Texture("menus/acercaDe/arte.png")));

        ImageButton btnBack = new ImageButton(trdBack);
        Image desInfo = new Image(trdDes);
        Image desAni = new Image(trdAni);

        btnBack.setPosition(0, ALTO - btnBack.getHeight());
        desInfo.setPosition(ANCHO/2 - 10 , ALTO - desInfo.getHeight() - 10);
        desAni.setPosition(10,10);

        //Evento de boton.
        btnBack.addListener(new ClickListener(){
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    //INSTRUCCIONE
                                    escenaHUD.addAction(Actions.sequence(Actions.moveBy(-texturaFondo.getWidth(),0,1 ),Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal(juego,musica));
                                        }
                                    })));

                                }
                            }
        );

        escenaHUD.addActor(btnBack);
        escenaHUD.addActor(desAni);
        escenaHUD.addActor(desInfo);
        Gdx.input.setInputProcessor(escenaHUD);

    }
    @Override
    public void render(float delta) {
        borrarPantalla();
        //batch escalaTodo de acuerdo a la visat y la camara
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.end();

        escenaHUD.act(Gdx.graphics.getDeltaTime());
        escenaHUD.draw();
    }


    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        texturaFondo.dispose(); //liberar
    }
}
