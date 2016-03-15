package fr.algofi.example.swift.prowide.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import org.junit.Test;

public class MT900ReaderTest {

	
	/**
	 * test the MT900 SWIFT Reader constructor create an object without any exception
	 * @throws IOException
	 */
	@Test
	public void test_constructor() throws IOException {
		// input
		final String resourcePath = "/swift_mt900_example.txt";
		final InputStream inputStream = getClass().getResourceAsStream( resourcePath);
		// call
		final MT900Reader mt900Reader = new MT900Reader(inputStream);
		// assertions
		// test is successful if no exception is thrown
		assertNotNull(mt900Reader);
	}

	/**
	 * test IOException is thrown in the case the SWIFT message input stream cannot be read
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public void test_constructor_throwsException() throws IOException {
		// input
		final InputStream inputStream = mock( InputStream.class );
		when( inputStream.read() ).thenThrow(new IOException());
		when( inputStream.read( any( byte[].class) ) ).thenThrow(new IOException());
		when( inputStream.read( any( byte[].class), anyInt(), anyInt() ) ).thenThrow(new IOException());
		
		// call
		final MT900Reader mt900Reader = new MT900Reader(inputStream);
		// assertions
		// test is successful if no exception is thrown
		assertNotNull(mt900Reader);
	}

	/**
	 * test that the debit value read date is the good one
	 * @throws IOException
	 */
	@Test
	public void test_getValueDate() throws IOException {
		// input
		final String resourcePath = "/swift_mt900_example.txt";
		final InputStream inputStream = getClass().getResourceAsStream( resourcePath);
		// prepare
		final MT900Reader mt900Reader = new MT900Reader(inputStream);
		// call
		final Calendar actualValueDate = mt900Reader.getDebitValueDate();
		// assertions
		assertEquals(2015, actualValueDate.get(Calendar.YEAR ));
		assertEquals(0, actualValueDate.get(Calendar.MONTH ));
		assertEquals(1, actualValueDate.get(Calendar.DAY_OF_MONTH ));
	}

	/**
	 * test that the debit curreny code read is the good one
	 * @throws IOException
	 */
	@Test
	public void test_getCurrecy() throws IOException {
		// input
		final String resourcePath = "/swift_mt900_example.txt";
		final InputStream inputStream = getClass().getResourceAsStream( resourcePath);
		// prepare
		final MT900Reader mt900Reader = new MT900Reader(inputStream);
		// call
		final Currency actualCurrency = mt900Reader.getDebitCurrency();
		// assertions
		assertEquals( "GBP", actualCurrency.getCurrencyCode());
	}

	/**
	 * test that the debt amount read is the good one.
	 * @throws IOException
	 */
	@Test
	public void test_getAmount() throws IOException {
		// input
		final String resourcePath = "/swift_mt900_example.txt";
		final InputStream inputStream = getClass().getResourceAsStream( resourcePath);
		// prepare
		final MT900Reader mt900Reader = new MT900Reader(inputStream);
		// call
		final Number actualAmount = mt900Reader.getDebitAmount();
		// assertions
		assertEquals( new BigDecimal( "1234.09" ), actualAmount);
	}

}
