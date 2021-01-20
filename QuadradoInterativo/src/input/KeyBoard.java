package input;
import cena.Cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
/**
 *
 * @author Siabreu
 */
public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        
        System.out.println("Key pressed: " + e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        if(e.getKeyChar() == 'a')
            System.out.println("Pressionou tecla a");
        
        if(e.getKeyChar() == 'T')
            cena.tx += 0.1f;
        
        if(e.getKeyChar() == 't')
            cena.tx -= 0.1f;
        
        if(e.getKeyChar() == 'E')
            cena.sx += 0.1f;
        
        if(e.getKeyChar() == 'e')
            cena.sx -= 0.1f;
        
        if(e.getKeyChar() == 'r')
            cena.rot += 45f;
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
