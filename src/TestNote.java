import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Note;

public class TestNote extends LockedTestCase{

	private static final double MARGIN = 1E-6;
	private Note note;

	@Override
	protected void setUp(){
		try {
			assert 1/0 == 42 : "OK";
			System.err.println("Assertions must be enabled to use this test suite.");
			System.err.println("In Eclipse: add -ea in the VM Arguments box under Run>Run Configurations>Arguments");
			assertFalse("Assertions must be -ea enabled in the Run Configuration>Arguments>VM Arguments",true);
		} catch (ArithmeticException ex) {
			return;
		}
	}

	protected <T> void assertException(String excName, Supplier<T> producer) {
		try {
			T result = producer.get();
			assertFalse("Should have thrown an exception, not returned " + result,true);
		} catch (RuntimeException ex) {
			assertEquals("Wrong kind of exception thrown",excName,ex.getClass().getSimpleName());
		}
	}

	protected <T> void assertException(Class<?> excClass, Supplier<T> producer) {
		try {
			T result = producer.get();
			assertFalse("Should have thrown an exception, not returned " + result,true);
		} catch (RuntimeException ex) {
			if (!excClass.isInstance(ex)) {
				assertFalse("Wrong kind of exception thrown: "+ ex.getClass().getSimpleName(),true);
			}
		}		
	}

	// testAn/Bn: testing toMidi
	
	public void testA0() {
		assertEquals("What is c0 as a MIDI value?", Ti(1418336421), Note.toMidi("c0"));
	}
	
	public void testA1() {
		assertEquals("What is g10 as a MIDI value?", Ti(2026538903), Note.toMidi("g10"));
	}
	
	public void testA2() {
		assertEquals("What is rest as a MIDI value?", Ti(1036411531), Note.toMidi("rest"));		
	}
	
	public void testA3() {
		assertEquals("What is g0 as a MIDI value?", Ti(1488048343), Note.toMidi("g0"));
	}
	
	public void testA4() {
		assertEquals(8, Note.toMidi("g#0"));
	}
	
	public void testA5() {
		assertEquals(21, Note.toMidi("a1"));
	}
	
	public void testA6() {
		assertEquals(34, Note.toMidi("a#2"));
	}
	
	public void testA7() {
		assertEquals(47, Note.toMidi("b3"));
	}
	
	public void testA8() {
		assertEquals(48, Note.toMidi("c04"));
	}
	
	public void testA9() {
		assertEquals(61, Note.toMidi("c#05"));
	}
	
	public void testB0() {
		assertEquals(74, Note.toMidi("d6"));
	}
	
	public void testB1() {
		assertEquals(87, Note.toMidi("d#7"));
	}
	
	public void testB2() {
		assertEquals(100, Note.toMidi("e8"));
	}
	
	public void testB3() {
		assertEquals(113, Note.toMidi("f9"));
	}
	
	public void testB4() {
		assertEquals(126, Note.toMidi("f#10"));
	}

	// toMidi - Nasty
	public void testB5() {
		assertException(IllegalArgumentException.class,() -> Note.toMidi("b#5"));
	}
	
	public void testB6() {
		assertException(IllegalArgumentException.class,() -> Note.toMidi("0"));
	}
	
	public void testB7() {
		assertException(IllegalArgumentException.class,() -> Note.toMidi("g#10"));
	}
	
	public void testB8() {
		assertException(IllegalArgumentException.class,() -> Note.toMidi(""));
	}
	
	public void testB9() {
		assertException(IllegalArgumentException.class,() -> Note.toMidi("g1 0"));
	}

	/// testCn/Dn: toPitchName tests
	
	public void testC0() {
		assertEquals("What is MIDI value 0 as a pitch string?", Ts(1222673533), Note.toPitchName(0));
	}
	
	public void testC1() {
		assertEquals("What is MIDI value 127 as a pitch string?", Ts(906414704), Note.toPitchName(127));
	}
	
	public void testC2() {
		assertEquals("What is MIDI value 128 as a pitch stringe?", Ts(1954336753), Note.toPitchName(128));
	}
	
	public void testC3() {
		assertEquals("What is MIDI value 4 as a pitch string?", Ts(359559134), Note.toPitchName(4));
	}
	
	public void testC4() {
		assertEquals("c#0", Note.toPitchName(1));
	}

	public void testC5() {
		assertEquals("f1", Note.toPitchName(17));
	}
	
	public void testC6() {
		assertEquals("f#2", Note.toPitchName(30));
	}
	
	public void testC7() {
		assertEquals("g3", Note.toPitchName(43));
	}
	
	public void testC8() {
		assertEquals("g#4", Note.toPitchName(56));
	}
	
	public void testC9() {
		assertEquals("a5", Note.toPitchName(69));
	}
	
	public void testD0() {
		assertEquals("a#6", Note.toPitchName(82));
	}
	
	public void testD1() {
		assertEquals("b7", Note.toPitchName(95));
	}
	
	public void testD2() {
		assertEquals("c8", Note.toPitchName(96));
	}
	
	public void testD3() {
		assertEquals("c#9", Note.toPitchName(109));
	}
	
	public void testD4() {
		assertEquals("d10", Note.toPitchName(122));
	}
	
	public void testD5() {
		assertEquals("d#10", Note.toPitchName(123));
	}

	// toPitch - Nasty
	public void testD6() {
		assertException("IllegalArgumentException",() -> Note.toPitchName(-1));
	}
	
	public void testD7() {
		assertException("IllegalArgumentException",() -> Note.toPitchName(129));
	}
	
	public void testD8() {
		assertException("IllegalArgumentException",() -> Note.toPitchName(-2000));
	}
	
	public void testD9() {
		assertException("IllegalArgumentException",() -> Note.toPitchName(1970));
	}

	
	/// testEn tests of Note constructor with string,double arguments

	public void testE0() {
		// What exception should be thrown here?
		assertException(Ts(2052943245),() -> new Note("c0",0));
	}
	
	public void testE1() {
		// What exception should be thrown here?
		assertException(Ts(2097267879),() -> new Note("g3",8.00000001));		
	}
	
	public void testE2() {
		assertException("IllegalArgumentException", () -> new Note("a10",1.0));
	}
	
	public void testE3() {
		assertException("IllegalArgumentException", () -> new Note("f 1", 0.5));
	}
	
	public void testE4() {
		assertException("IllegalArgumentException", () -> new Note("a3", -1.0));	
	}
	
	public void testE5() {
		Note n = new Note("a01", 1.0);
		assertEquals("a1", n.getPitchName());
		assertEquals(1.0, n.getDuration());
		assertEquals(21, n.getMidiPitch());
	}
	
	public void testE6() {
		Note n = new Note("c00", 0.015625);
		assertEquals("c0", n.getPitchName());
		assertEquals(0.015625, n.getDuration());
		assertEquals(0, n.getMidiPitch());
	}
	
	public void testE7() {
		Note n = new Note("g10", 8);
		assertEquals("g10", n.getPitchName());
		assertEquals(8.0, n.getDuration());
		assertEquals(127, n.getMidiPitch());
	}
	
	public void testE8() {
		Note n = new Note("rest", 1.5);
		assertEquals("rest", n.getPitchName());
		assertEquals(1.5, n.getDuration());
		assertEquals(128, n.getMidiPitch());
	}
	
	public void testE9() {
		Note n = new Note("c5", 0.5);
		assertEquals("c5", n.getPitchName());
		assertEquals(0.5, n.getDuration());
		assertEquals(60, n.getMidiPitch());
	}
	

	/// testFn: tests of the Note(int,double) constructor

	// MIDI Value Construction
	public void testF0() {
		Note n = new Note(0, 0.015625);
		assertEquals("c0", n.getPitchName());
		assertEquals(0.015625, n.getDuration());
		assertEquals(0, n.getMidiPitch());
	}
	
	public void testF1() {
		Note n = new Note(127, 8);
		assertEquals("g10", n.getPitchName());
		assertEquals(8.0, n.getDuration());
		assertEquals(127, n.getMidiPitch());
	}
	
	public void testF2() {
		Note n = new Note(128, 1.5);
		assertEquals("rest", n.getPitchName());
		assertEquals(1.5, n.getDuration());
		assertEquals(128, n.getMidiPitch());
	}
	
	public void testF3() {
		Note n = new Note(18, 1.0);
		assertEquals("f#1", n.getPitchName());
		assertEquals(1.0, n.getDuration());
		assertEquals(18, n.getMidiPitch());
	}
	
	public void testF4() {
		Note n = new Note(83, 0.5);
		assertEquals("b6", n.getPitchName());
		assertEquals(0.5, n.getDuration());
		assertEquals(83, n.getMidiPitch());
	}
	
	public void testF5() {
		assertException("IllegalArgumentException",() -> new Note(129,1.0));
	}
	
	public void testF6() {
		assertException("IllegalArgumentException",() -> new Note(-1,1.0));
	}
	
	public void testF7() {
		assertException("IllegalArgumentException",() -> new Note(60,-0.5));
	}
	
	public void testF8() {
		assertException("IllegalArgumentException",() -> new Note(12,8.000000001));
	}
	
	public void testF9() {
		assertException("IllegalArgumentException",() -> new Note(67,0));
	}


	/// testGn: tests of toString
	
	public void testG0() {
		assertEquals("c5 x 1.0", new Note("c5",1.0).toString());
	}
	
	public void testG1() {
		assertEquals(Ts(1472697116), new Note("c04", 0.50).toString());
	}
	
	public void testG2() {
		assertEquals("c3 x 0.015625", new Note("c3", 1.0/64).toString());
	}
	
	public void testG3() {
		assertEquals("c2 x 8.0", new Note("c2", 8).toString());
	}
	
	public void testG4() {
		assertEquals("c0 x 1.00001", new Note(0, 1.00001).toString());
	}
	
	public void testG5() {
		assertEquals("g10 x 0.03125", new Note(127, 1.0/32).toString());
	}
	
	public void testG6() {
		assertEquals("rest x 0.25", new Note(128, 1.0/4).toString());
	}
	
	public void testG7() {
		assertEquals(Ts(698360569), new Note("rest", 4).toString());
	}
	
	public void testG8() {
		assertEquals("d0 x 0.123456789", new Note(2, 0.123456789).toString());
	}
	
	public void testG9() {
		assertEquals("f10 x 0.12345678912345678", new Note(125, 0.123456789123456789).toString());
	}
	
	
	/// testHn: tests of stretch
	
	public void testH0() {
		note = new Note("c0", 0.5);
		assertEquals(1.0, note.stretch(2.0).getDuration());
	}
	
	public void testH1() {
		note = new Note("c0", 0.5);
		assertEquals(0.125, note.stretch(0.25).getDuration());
	}
	
	public void testH2() {
		note = new Note("c0", 0.5);
		assertEquals(8.0, note.stretch(16.0).getDuration());
	}
	
	public void testH3() {
		note = new Note("c0", 0.5);
		assertEquals(0.015625, note.stretch(1/32.0).getDuration());
	}
	
	public void testH4() {
		// Pitch remains unchanged
		note = new Note("c0", 0.5);
		assertEquals("c0", note.stretch(2.0).getPitchName());
	}
	
	public void testH5() {
		note = new Note("rest", 2.0);
		assertEquals("rest x 0.5", note.stretch(1.0/4).toString());
	}
	
	public void testH6() {
		note = new Note("rest", 2.0);
		assertEquals("rest x 0.015625", note.stretch(1.0/128).toString());
	}

	public void testH7() {
		note = new Note("c0", 0.125);
		assertException("IllegalArgumentException", () -> note.stretch(1.0/9));
	}

	public void testH8() {
		note = new Note("rest", 5);
		assertException("IllegalArgumentException", () -> note.stretch(2));
	}
	
	public void testH9() {
		note = new Note("f3", 2.0);
		assertException("IllegalArgumentException",() -> note.stretch(0));
		assertException("IllegalArgumentException",() -> note.stretch(-0.5));
	}
	
	/// testIn: transpose tests
	
	public void testI0() {
		note = new Note("f#5", 1.5);
		assertEquals(Ts(1210436445), note.transpose(1).getPitchName());
	}
	
	public void testI1() {
		note = new Note("f#5", 1.5);
		assertEquals(Ts(766049360), note.transpose(2).getPitchName());
	}
	
	public void testI2() {
		note = new Note("f#5", 1.5);
		assertEquals(Ts(1805625595), note.transpose(-1).getPitchName());
	}
	
	public void testI3() {
		note = new Note("f#5", 1.5);
		assertEquals("c3", new Note("c4",1.5).transpose(-12).getPitchName());
	}
	
	public void testI4() {
		note = new Note("f#5", 1.5);
		assertEquals("c5", new Note("c4",1.5).transpose(12).getPitchName());
	}
	
	public void testI5() {
		note = new Note("f#5", 1.5);
		assertEquals("g10", new Note("c0",1.5).transpose(127).getPitchName());
	}
	
	public void testI6() {
		note = new Note("f#5", 1.5);
		assertEquals("rest", new Note("rest",1.5).transpose(3).getPitchName());
	}
	
	public void testI7() {
		note = new Note("f#5", 1.5);
		
		// Duration remains unchanged
		assertEquals(1.5, note.transpose(1).getDuration(), MARGIN);
	}

	public void testI8() {
		assertException("IllegalArgumentException",() -> new Note(127, 1.0).transpose(1));
	}

	public void testI9() {
		assertException("IllegalArgumentException",() -> new Note(0, 1.0).transpose(-1));
	}


	/// testJ0: tests of equals and hashcode
	
	public void testJ0() {
		assertEquals(new Note("f#4 x 0.50"),new Note("f#04",0.5));
	}

	public void testJ1() {
		assertFalse(new Note("f#4",0.5).equals((Object)"f#4 x 0.5"));
	}

	public void testJ2() {
		assertFalse(new Note("f#4",0.5).equals(new Note("f#4",0.5625)));
	}

	public void testJ3() {
		assertEquals(new Note("rest",0.25),(Object)new Note("rest",0.25));
	}

	public void testJ4() {
		assertFalse(new Note("c0",0.0625).equals((Object)new Note("c#0",0.0625)));
	}

	public void testJ5() {
		assertFalse(new Note("c0",0.0625).equals((Object)Double.valueOf(0.0625)));
	}

	private static final int SAMPLE = 10000;
	
	public void testJ6() {
		Random r = new Random();
		for (int i=0; i < SAMPLE; ++i) {
			int p = r.nextInt(128);
			double d = (r.nextInt(511)+1) * 0.015625;
			Note n1 = new Note(p,d);
			Note n2 = new Note(p,d+0.000000000000001);
			assertFalse(n2 + " is not the same as " + n1, n1.equals(n2));
		}
	}
	
	// hashCode
	public void testJ7() {
		// check that all normal notes have different hash codes
		Map<Integer,Note> map = new HashMap<>();
		
		for (int p = 0; p <= 128; ++p) {
			for (double d = 1.0/64; d <= 8; d += 1.0/64) {
				Note n = new Note(p, d);
				int h = n.hashCode();
				Note prev = map.put(h, n);
				if (prev != null)
					assertFalse("Both " + prev + " and " + n + " have hash code " + h, true);
			}
		}
	}
	
	public void testJ8() {
		// check that code doesn't use "toString"
		assertFalse(new Note("f8 x 4.556032902872668").hashCode() == new Note("d9 x 1.6213654825599657").hashCode());
	}
	
	public void testJ9() {
		// check that code doesn't use "toString"
		assertFalse(new Note(60,2.351882563733995).hashCode() == new Note(60, 5.637557381734443).hashCode());
	}
}
