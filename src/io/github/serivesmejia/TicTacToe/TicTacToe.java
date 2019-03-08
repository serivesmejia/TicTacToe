package io.github.serivesmejia.TicTacToe;

import static io.github.serivesmejia.TicTacToe.frame_cpu.game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;


public class TicTacToe{

public static SoundSystem sSyst;
public static HashMap cfg = new HashMap();
public static Yaml yaml = new Yaml();
public static io.github.serivesmejia.TicTacToe.frame frm;

    public static void main(String[] args) {
        String appdata = System.getenv("APPDATA");  
        File appdataf = new File(appdata+"/TicTac-S");
        try
        {
        SoundSystemConfig.addLibrary( LibraryJavaSound.class );
        SoundSystemConfig.setCodec( "ogg", CodecJOrbis.class );
        SoundSystemConfig.setCodec( "wav", CodecWav.class );
        SoundSystemConfig.setSoundFilesPackage("/");
        }
        catch( SoundSystemException e)
        {
        System.err.println("Error while initalizing audio system plug-ins " + e.getMessage() );
        }
        sSyst = new SoundSystem();
        
        File cfgf = new File(appdataf+"/stats.yml");
        
        if(!cfgf.exists()){
            new File(appdata+"/TicTac-S").mkdir(); 
            cfg.put("plays", 0);
            cfg.put("XW", 0); 
            cfg.put("OW", 0);
            cfg.put("CPUW", 0);
            cfg.put("clicks", 0);
            cfg.put("ties", 0);
            System.out.println("Creando archivo stats.yml");
            DumpWriterCFG(cfgf, cfg);
            try {
                loadFromFile(cfgf);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{           
            try {
                System.out.println("Cargando estadisticas...");
                loadFromFile(cfgf);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        int count = 1;
        while(count < 10){
           frame.game.put(count, "D"); 
           count = count + 1;
        }  
        
        frm = new frame();
        frm.setVisible(true);
    }
    
        public static void loadFromFile(File file) throws FileNotFoundException{
        
        try {
            InputStream ios = new FileInputStream(file);
            
            Map<String, Object> result = (Map<String, Object>)yaml.load(ios);
            
            for (Object name : result.keySet()) {
                cfg.put(name.toString(), result.get(name).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(cfg);
    }
    
    public static boolean DumpWriterCFG(File file, HashMap hash) {
            try {
            
            FileWriter fw = new FileWriter(file);
            for (Object name : hash.keySet()) {
            fw.write(name + ": '" + hash.get(name).toString() +"'" + "\n");
            }
            fw.close();
            return true;
        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
            return false;
        }
}

    public static frame getFrame(){ return frm; }

    
}
