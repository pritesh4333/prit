package in.co.vyaparienterprise.ui.activity.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import in.co.vyaparienterprise.R;


public class Reports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Button excel =(Button)findViewById(R.id.exel);
        Button csv =(Button)findViewById(R.id.CSV);
        Button pdf =(Button)findViewById(R.id.pdf);
        excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if available and not read only


                boolean success = false;

                //New Workbook
                Workbook wb = new HSSFWorkbook();

                Cell c = null;

                //Cell style for header row
                CellStyle cs = wb.createCellStyle();
                cs.setFillForegroundColor(HSSFColor.LIME.index);
                cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                //New Sheet
                Sheet sheet1 = null;
                sheet1 = wb.createSheet("myOrder");



                // Generate column headings
                Row row = sheet1.createRow(0);
                Row row1 = sheet1.createRow(6);

                c = row1.createCell(1);
                c.setCellValue("Row1 Item");
                c.setCellStyle(cs);

                c = row.createCell(0);
                c.setCellValue("Item Number");
                c.setCellStyle(cs);

                c = row.createCell(1);
                c.setCellValue("Quantity");
                c.setCellStyle(cs);

                c = row.createCell(2);
                c.setCellValue("Price");
                c.setCellStyle(cs);

                c = row.createCell(4);
                c.setCellValue("Price");
                c.setCellStyle(cs);

                sheet1.setColumnWidth(0, (15 * 500));
                sheet1.setColumnWidth(1, (15 * 500));
                sheet1.setColumnWidth(2, (15 * 500));

                // Create a path where we will place our List of objects on external storage
                File file = new File(getApplicationContext().getExternalFilesDir(null), "TEST.xlsx");
                FileOutputStream os = null;

                try {
                    os = new FileOutputStream(file);
                    wb.write(os);
                    Log.w("FileUtils", "Writing file" + file);
                    success = true;
                } catch (IOException e) {
                    Log.w("FileUtils", "Error writing " + file, e);
                } catch (Exception e) {
                    Log.w("FileUtils", "Failed to save file", e);
                } finally {
                    try {
                        if (null != os)
                            os.close();
                    } catch (Exception ex) {
                    }
                }
            }
        });
        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportEmailInCSV();
            }
        });
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        }
    public void exportEmailInCSV()  {
        {

            File folder = new File(Environment.getExternalStorageDirectory()
                    + "/Folder");

            boolean var = false;
            if (!folder.exists())
                var = folder.mkdir();

            System.out.println("" + var);


            final String filename = folder.toString() + "/" + "Test.csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    Reports.this, contentTitle, "even geduld aub...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {




                }
            };

            new Thread() {
                public void run() {
                    try {

                        FileWriter fw = new FileWriter(filename);

                     //   Cursor cursor = db.selectAll();

                        fw.append("No");
                        fw.append(',');

                        fw.append("code");
                        fw.append(',');

                        fw.append("nr");
                        fw.append(',');

                        fw.append("Orde");
                        fw.append(',');

                        fw.append("Da");
                        fw.append(',');

                        fw.append("Date");
                        fw.append(',');

                        fw.append("Leverancier");
                        fw.append(',');

                        fw.append("Baaln");
                        fw.append(',');

                        fw.append("asd");
                        fw.append(',');

                        fw.append("Kwaliteit");
                        fw.append(',');

                        fw.append("asd");
                        fw.append(',');

                        fw.append('\n');
                        fw.append("Kwaliteit");
                        fw.append(',');

                        fw.append("asd");
                        fw.append(',');

//                        if (cursor.moveToFirst()) {
//                            do {
//                                fw.append(cursor.getString(0));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(1));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(2));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(3));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(4));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(5));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(6));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(7));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(8));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(9));
//                                fw.append(',');
//
//                                fw.append(cursor.getString(10));
//                                fw.append(',');
//
//                                fw.append('\n');
//
//                            } while (cursor.moveToNext());
//                        }
//                        if (cursor != null && !cursor.isClosed()) {
//                            cursor.close();
//                        }

                         fw.flush();
                        fw.close();

                    } catch (Exception e) {
                    }
                    handler.sendEmptyMessage(0);
                    progDailog.dismiss();
                }
            }.start();

        }
    }
}
