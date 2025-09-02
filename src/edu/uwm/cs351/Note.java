package edu.uwm.cs351;

/**
 * The Immutable Class Note.
 */
public class Note {

	/** Static Constants */
	public static final int DEFAULT_INTENSITY = 50;
	public static final int REST_PITCH = 128; // First illegal pitch, used for rests.
	private static final int PITCHES_PER_OCTAVE = 12;
	private static final String[] NOTE_LETTERS = {"c","c#","d","d#","e","f","f#","g","g#","a","a#","b"};
	private static final double MIN_DURATION = 1.0/64, // One sixty-fourth
								MAX_DURATION = 8.0; // Eight whole notes

	/** Fields (Immutable) */
	private final String pitchName;
	private final int midiValue;
	private final double duration;

	/**
	 * Instantiates a new note based on a string denoting note letter and octave.
	 *
	 * @param pitchName the pitch name (e.g. "f#6")
	 * @param duration the duration
	 * @throws NullPointerException if pitch is null
	 * @throws IllegalArgumentException if:
	 * 				1. The pitch name parameter is malformed or out of range.
	 * 				2. The duration parameter is out of range.
	 */
	public Note(String pitchName, double duration) {
		this(""); // TODO: change this
		// Recommended: First implement toMidi(String).
	}

	/**
	 * Instantiates a new note based on MIDI value.
	 * @param midiValue the MIDI value (e.g. 68)
	 * @param duration the duration
	 * @throws IllegalArgumentException if:
	 * 				1. The MIDI pitch parameter is out of range.
	 * 				2. The duration parameter is out of range.
	 */
	public Note(int midiValue, double duration) {
		this(""); // TODO: change this
		// Recommended: First implement toPitchName(int).
	}

	/**
	 * Instantiates a new note from a String matching the format of Note's toString() method.
	 *
	 * @param note the string representation, must not be null
	 * 
	 * @throws NumberFormatException if duration representation cannot be parsed as double
	 * @throws IllegalArgumentException if string has the wrong format or if the values ij the parts are not allowed.
	 */
	public Note(String note) {
		String[] parts = note.split(" x ");
		if (parts.length != 2) throw new IllegalArgumentException("Bad format: need one instance of ' x ': " + note);
		Note n = new Note(parts[0], Double.parseDouble(parts[1]));
		this.duration = n.duration;
		this.midiValue = n.midiValue;
		this.pitchName = n.pitchName;
	}

	/**
	 * Converts a pitch string to a MIDI value.
	 * The pitch "rest" should return {@link #REST_PITCH}.
	 *
	 * @param pitch the pitch to convert
	 * @throws NullPointerException if pitch is null
	 * @throws IllegalArgumentException is the String is not a legal pitch
	 * @return the MIDI value
	 */
	public static int toMidi(String pitch) {
		return -1; // TODO: change this
	}

	/**
	 * Converts a MIDI value to a pitch name.
	 * The MIDI value 128 should return "rest".
	 * 
	 * @param midiValue the MIDI value to convert
	 * @throws IllegalArgumentException if the MIDI value is outside of legal range
	 * @return the pitch name
	 */
	public static String toPitchName(int midiValue) {
		return null; // TODO: change this
	}

	/**
	 * Gets the pitch name of this note.
	 *
	 * @return the pitch name
	 */
	public String getPitchName() { return pitchName; }

	/**
	 * Gets the MIDI value of this note.
	 *
	 * @return the MIDI value
	 */
	public int getMidiPitch() { return midiValue; }

	/**
	 * Gets the duration of this note.
	 *
	 * @return the duration
	 */
	public double getDuration() { return duration; }
	

	/**
	 * Returns a string representation of this Note.
	 * It should follow the format found in songs/InMyLife.song, namely:
	 * 	For a Note with pitch "g#4" and duration 1.0625 -> "g#4 x 1.0625"
	 * NB1: Identical spacing and format are important!
	 * NB2: For a "rest" note, the same format must be used (including duration).
	 * 
	 * @return the string representation
	 */
	@Override // implementation
	public String toString() {
		return null; // TODO
	}

	/**
	 * Returns a new note with the same pitch, but with its duration multiplied by the parameter.
	 * 
	 * @param factor the amount to scale by
	 * @throws IllegalArgumentException if resultant duration is outside of valid range
	 * @return the stretched note
	 */
	public Note stretch(double factor) {
		return null; // TODO
	}

	// TODO: add documentation comment. "Source > Generate Element Comment" can help
	public Note transpose(int interval) {
		return null; // TODO
	}

	// TODO: more to do.  (Read homework assignment.)
}
