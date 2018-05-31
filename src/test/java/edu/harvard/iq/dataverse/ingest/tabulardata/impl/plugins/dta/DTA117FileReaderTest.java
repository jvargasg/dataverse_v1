package edu.harvard.iq.dataverse.ingest.tabulardata.impl.plugins.dta;

import edu.harvard.iq.dataverse.ingest.tabulardata.TabularDataIngest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;

public class DTA117FileReaderTest {

    DTA117FileReader instance = new DTA117FileReader(null);
    File nullDataFile = null;

    @Test
    public void testAuto() throws IOException {
        // From https://www.stata-press.com/data/r13/auto.dta
        // `strings` shows "<stata_dta><header><release>117</release>"
        TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("scripts/search/data/tabular/stata13-auto.dta"))), nullDataFile);
        assertEquals("application/x-stata", result.getDataTable().getOriginalFileFormat());
        assertEquals("STATA 13", result.getDataTable().getOriginalFormatVersion());
        assertEquals(12, result.getDataTable().getDataVariables().size());
    }

    // TODO: A 2.9 KB, this "HouseImputing" Stata 13 file is nice and small and it would be great to get it working.
    @Ignore
    @Test
    public void testHouse() throws IOException {
        // https://dataverse.harvard.edu/file.xhtml?fileId=2865667
        TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/tmp/HouseImputingCivilRightsInfo.dta"))), nullDataFile);
        assertEquals("application/x-stata", result.getDataTable().getOriginalFileFormat());
        assertEquals("STATA 13", result.getDataTable().getOriginalFormatVersion());
        assertEquals(12, result.getDataTable().getDataVariables().size());
    }

    //For now this test really just shows that we can parse a file with strls
    //There is no obvious output of parsing the strls as the are just added to our tab delimited file like other data
    //The test file use was based off auto.dta, adding an extra strL variable (column).
    //See https://github.com/IQSS/dataverse/issues/1016 for info on Stata 13 strl support
    @Test
    public void testStrls() throws Exception {
        TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("scripts/search/data/tabular/stata13-auto-withstrls.dta"))), nullDataFile);
        assertEquals("application/x-stata", result.getDataTable().getOriginalFileFormat());
        assertEquals("STATA 13", result.getDataTable().getOriginalFormatVersion());
        assertEquals(13, result.getDataTable().getDataVariables().size());
    }

}

//Below is a record of stata 13 files I tried downloading and testing manually with the errors they threw. Things to maybe test later? -MAD
//
// TODO: This file was downloaded at random. We could keep trying to get it to ingest.
// https://dataverse.harvard.edu/file.xhtml?fileId=2775556 Stata 14: mm_public_120615_v14.dta
// For this file "hasSTRLs" is true so it might be nice to get it working.
//        
//ERROR:                 throw new IOException("Checking section tags across byte buffers not yet implemented.");
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/tmp/WolfordKimReplication.dta"))), nullDataFile);
//        
//ERROR:                throw new IOException("DataReader.readBytes called to read zero or negative number of bytes.");
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/tmp/Foos_de_Rooij_AJPS_data_10Jun2016.dta"))), nullDataFile); //orig name Foos&de Rooij_AJPS_data_10Jun2016.dta
//          
//ERROR:                 throw new IOException("Checking section tags across byte buffers not yet implemented.");
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/home/madunlap/Downloads/L_Saxony_national_2013.dta"))), nullDataFile);
//         
//ERROR:                 throw new IOException("Checking section tags across byte buffers not yet implemented.");
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/tmp/Football+2016+Replication+Data+10-13-17+(Stata+13).dta"))), nullDataFile);
//        
//Is a good example of things working in stata 11 but not stata 13
//https://dataverse.unc.edu/dataset.xhtml?persistentId=doi:10.15139/S3/12169
//these ones seem to work but not strls
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/home/madunlap/Downloads/Appendix data.dta"))), nullDataFile);        
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/home/madunlap/Downloads/Final state data.dta"))), nullDataFile);
//        
//works, no strls
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/home/madunlap/Downloads/Hitt_precedent_LASR_replication_data.dta"))), nullDataFile);
//        
//ERROR: Could not read opening tag <stata_dta>
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/home/madunlap/Downloads/Free Exercise Cases Cert (replication).dta"))), nullDataFile);
//        
//I modified ans saved created this file with stata 15 saving as stata 13. But it doesn't work as it seems to actually save as a stata 14 file
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/home/madunlap/Downloads/13 with strl (Hitt_precedent_LASR_replication_data).dta"))), nullDataFile);
//        
//this one I modified and saved as a stata 12/11 file, but under the hood it saves as 117 which is actually stata 13? I don't understand...
//TabularDataIngest result = instance.read(new BufferedInputStream(new FileInputStream(new File("/home/madunlap/Downloads/1211 with strl (Hitt_precedent_LASR_replication_data).dta"))), nullDataFile);
