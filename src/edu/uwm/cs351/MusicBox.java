package edu.uwm.cs351;
import javax.sound.midi.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The Class MusicBox.
 */
public class MusicBox implements MetaEventListener {

	/** Static Constants */
    private static final int TICKS_PER_QUARTER_NOTE = 512; // each tick is a 16th note
    public static final int END_OF_TRACK_MESSAGE = 47;

    /** MIDI fields */
    private Synthesizer synthesizer;
    private Sequencer sequencer;
    
    /**
     * The main method.
     *
     * @param args the arguments to launch (ignored)
     */
    public static void main(String[] args) {
    	try {
    		String filename = args[0];
    		double multiplier = 1.0;
    		int interval = 0;
    		
    		int i = 0;
    		while (++i < args.length) {
    			if (args[i].startsWith("stretch="))
    				multiplier = Double.parseDouble(args[i].split("=")[1]);
    			else if (args[i].startsWith("transpose="))
    				interval = Integer.parseInt(args[i].split("=")[1]);
    			else{
    				throw new IllegalArgumentException("Unknown argument: "+args[i]);
    			}
    				
    		}

    		new MusicBox().play(filename, multiplier, interval);
    	}
    	catch (Exception e) {
    		PrintWriter pw = new PrintWriter(System.out);
    		pw.println("Usage: MusicBox <Song File> [Arguments]");
    		pw.println("Arguments:");
    		pw.println("	stretch=<multiplier>");
    		pw.println("	transpose=<interval>\n");
    		e.printStackTrace(pw);
    		pw.flush();
    		pw.close();
			System.exit(1);
    	}
    }
    
    /**
     * Constructs a new MusicBox Midi System.
     */
    public MusicBox() {
    	try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            sequencer = MidiSystem.getSequencer(false);
            sequencer.open();
            sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
            sequencer.addMetaEventListener(this);
            for (Instrument instrument : synthesizer.getAvailableInstruments()) {
            	if (instrument.getName().trim().toLowerCase().equals("fingered bs.")) {
            		synthesizer.loadInstrument(instrument);
                    synthesizer.getChannels()[0].programChange(instrument.getPatch().getProgram());
                    break;
            	}
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
  	
  	/**
	   * Reads, parses, and plays the given song file.
	   * @param filename the song file to play
	   */
	  private void play(String filename, double multiplier, int interval) {
		Sequence sequence = null;
		
  		try (Scanner s = new Scanner(new File("./songs/"+filename))) {
  			String name = s.nextLine().trim();
  			float bpm = Float.parseFloat(s.nextLine().trim().split(" ")[0]);
  			sequencer.setTempoInBPM(bpm);
  			
  			sequence = new Sequence(Sequence.PPQ, TICKS_PER_QUARTER_NOTE);
  			Track track = sequence.createTrack();
  			long timestamp = 0;
  			
  			while (s.hasNextLine()) {
  				String line = s.nextLine().trim();
  				if (line.equals("") || line.startsWith("[")) continue;

  				Note note = new Note(line).stretch(multiplier).transpose(interval);
  				putNote(track, note, timestamp);
  				timestamp += toTicks(note.getDuration());
  			}
  			
  			sequencer.setSequence(sequence);
  			sequencer.start();
  			System.out.println("Playing " + name + (multiplier != 1.0 ? ", stretched by " + multiplier : "") +
  					(interval != 0 ? ", transposed by " + interval : "") + "...");
  		}
  		catch (FileNotFoundException e) { e.printStackTrace(); System.exit(1); }
  		catch (InvalidMidiDataException e) { e.printStackTrace(); System.exit(1); }
  	}


    /**
     * Puts the note into the track at the timestamp.
     *
     * @param track - the track to add the note to
     * @param note - the note to add
     * @param timestamp - tick value at which to place the note
     * @throws InvalidMidiDataException if midiNote is outside range [0, 127] or
     * intensity is outside range [0, 127]
     */
    private static void putNote(Track track, Note note, long timestamp) throws InvalidMidiDataException {
    	
    	int midiValue = note.getMidiPitch();
    	int intensity = Note.DEFAULT_INTENSITY;
    	
    	if (midiValue == 128) {
    		midiValue = 0; // rest note
    		intensity = 0;
    	}
    	
        ShortMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, 0, midiValue, intensity);
        ShortMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, 0, midiValue, 0);
        track.add(new MidiEvent(noteOn, timestamp));
        track.add(new MidiEvent(noteOff, timestamp + toTicks(note.getDuration())));
    }
    
    private static int toTicks(double duration) {
    	return (int) Math.round(duration / 0.25 * TICKS_PER_QUARTER_NOTE);
    }
    
    /* (non-Javadoc)
     * @see javax.sound.midi.MetaEventListener#meta(javax.sound.midi.MetaMessage)
     */
    @Override
    public void meta(MetaMessage msg) {
        if (msg.getType() == END_OF_TRACK_MESSAGE) {
        	System.out.println("Done.");
        	sequencer.stop();
        	sequencer.close();
        	synthesizer.close();
        	System.exit(0);
        }
    }
}
