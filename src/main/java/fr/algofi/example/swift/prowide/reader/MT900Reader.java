package fr.algofi.example.swift.prowide.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Currency;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.mt9xx.MT900;

public class MT900Reader {

	
	
	private final MT900 mt900;
	
	public MT900Reader( final InputStream inputStream) throws IOException {
		final SwiftParser parser = new SwiftParser( inputStream );
		final SwiftMessage swiftMessage = parser.message();
		if ( "900".equals( swiftMessage.getType() )) {
			throw new IllegalArgumentException( "Invalid SWIFT message type" );
		} else {
			mt900 = new MT900(swiftMessage);			
		}
	}
	
	
	public Calendar getDebitValueDate() {
		return mt900.getField32A().getDateAsCalendar();
	}
	
	public Currency getDebitCurrency() {
		return mt900.getField32A().getCurrencyAsCurrency();
	}
	
	public Number getDebitAmount() {
		return mt900.getField32A().getAmountAsNumber();
	}
	
	
}
