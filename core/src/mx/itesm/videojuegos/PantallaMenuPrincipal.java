package mx.itesm.videojuegos;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaMenuPrincipal extends Pantalla{
    private final Juego juego;

    //FONDO
    private Texture texturaFondo;

    //escena de (botones)
    private Stage escenaHUD;

    private Estado estado = Estado.NORMAL;
    private Pausa escenaOpciones;

    public PantallaMenuPrincipal (Juego juego) {
        this.juego = juego;
    }



    @Override
    public void show() {
        cargarTexturas();
        crearHUD();

    }

    private void crearHUD() {
        escenaHUD = new Stage(vista);
        escenaOpciones = new Pausa(vista, batch);
        //Boton jugar
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/btn_jugar.png")));
        TextureRegionDrawable trdAcercaDe = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/btn_acerca-de.png")));
        TextureRegionDrawable trdOpciones = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/btn_opciones.png")));

        ImageButton btnJugar = new ImageButton(trdJugar);
        ImageButton btnAcerecaDe = new ImageButton(trdAcercaDe);
        ImageButton btnOpciones = new ImageButton(trdOpciones);


        btnJugar.setPosition(375, 390);
        btnAcerecaDe.setPosition(375, 227);
        btnOpciones.setPosition(385, 102);

        //Evento de boton.
        btnJugar.addListener(new ClickListener(){
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    //INSTRUCCIONE
                                    juego.setScreen(new Nivel1(juego));
                                }
                            }
        );

        escenaHUD.addActor(btnJugar);


        btnAcerecaDe.addListener(new ClickListener(){
                                 @Override
                                 public void clicked(InputEvent event, float x, float y) {
                                     super.clicked(event, x, y);
                                     //INSTRUCCIONE
                                     juego.setScreen(new PantallaAcercaDe(juego));

                                 }
                             }
        );

        escenaHUD.addActor(btnAcerecaDe);


        btnOpciones.addListener(new ClickListener(){
                                 @Override
                                 public void clicked(InputEvent event, float x, float y) {
                                     super.clicked(event, x, y);
                                     //INSTRUCCIONE
                                     estado = Estado.PAUSA;
                                     escenaOpciones.crearOpcionesMenuPrincipal(juego);



                                 }
                             }
        );

        escenaHUD.addActor(btnOpciones);


        Gdx.input.setInputProcessor(escenaHUD);
    }

    private void cargarTexturas() {
        texturaFondo = new Texture( "menus/menus.jpg");
    }



    @Override
    public void render(float delta) {
        borrarPantalla();
        //batch escalaTodo de acuerdo a la visat y la camara
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.end();

        if (estado == Estado.PAUSA) {
            escenaOpciones.draw();
        } else {
            escenaHUD.draw();
        }
        System.out.println(estado);

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

enum Estado{
    PAUSA,
    NORMAL
}