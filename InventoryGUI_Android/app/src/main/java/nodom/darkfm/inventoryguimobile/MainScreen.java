package nodom.darkfm.inventoryguimobile;

import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
	Copyright (C) Salvador Pardiñas @ https://github.com/salvadorp2001 GPLv3
*/

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

    }
    private int cur_itm = 0;
    private String[] itm_nme = {"","","","","",""};
    private int[] itm_quant = {0,0,0,0,0,0};
    public void load_item(View vw)
    {

        EditText edtxt =(EditText)findViewById(R.id.editText2);
        itm_nme[cur_itm] = edtxt.getText().toString();
        EditText quanttxt = (EditText)findViewById(R.id.editText3);
        try {
            itm_quant[cur_itm] = Integer.parseInt(quanttxt.getText().toString());
        } catch (NumberFormatException o)
        {
            itm_quant[cur_itm] = 0;
        }
        EditText itmtxt = (EditText)findViewById(R.id.editText);
        int old_cur = cur_itm;
        cur_itm = Integer.parseInt(itmtxt.getText().toString())-1;
        if((cur_itm < 0) || (cur_itm >= itm_nme.length))
        {
            AlertDialog a = new AlertDialog.Builder(MainScreen.this,R.style.Theme_AppCompat_Dialog_Alert).setTitle("Wrong number").setMessage("Please use numbers between 1 and "+itm_nme.length + " or add new items").show();
            cur_itm = old_cur;
        }
        else {
            edtxt.setText(itm_nme[cur_itm]);
            quanttxt.setText(String.valueOf(itm_quant[cur_itm]));
        }
    }
    public void load_data(View vw)
    {


        FileInputStream f = null;
        try {
            f = new FileInputStream(Environment.getExternalStorageDirectory()+"/inv.idb");
            byte[] buffer = new byte[(int)f.getChannel().size()];
            f.read(buffer);
            String input = new String(Base64.decode(Arrays.copyOfRange(buffer,4,buffer.length),Base64.DEFAULT));
            List<String> in2 = Arrays.asList(input.split("\n"));
            String[] nms = new String[in2.size()];
            int[] qnt = new int[in2.size()];
            String[]datus;
            for(int i = 0; i < in2.size();i++)
            {
                datus = in2.get(i).split("\\|");
                nms[i] = datus[0].replaceAll("`","|");
                qnt[i] = Integer.parseInt(datus[1]);
            }
            itm_quant = qnt;
            itm_nme = nms;
            EditText edtxt = (EditText)findViewById(R.id.editText2);
            EditText quanttxt = (EditText)findViewById(R.id.editText3);
            cur_itm = 0;
            edtxt.setText(nms[0]);
            quanttxt.setText(String.valueOf(qnt[0]));
            EditText qntxf = (EditText)findViewById(R.id.editText);
            qntxf.setHint("Item Number/"+itm_nme.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void save_data(View vw)
    {

        List<String> to_save = new LinkedList<String>();
        for(int i = 0; i < itm_quant.length; i++)
        {
            if(!(itm_nme[i].isEmpty())){
                to_save.add(itm_nme[i].replaceAll("\\|","`")+"|"+String.valueOf(itm_quant[i]));
            }
        }
        String out_str = "";
        for(String xbx : to_save)
        {
            out_str += xbx + "\n";
        }
        AlertDialog b = new AlertDialog.Builder(this).setTitle("abc").setMessage("Out:\n"+out_str).show();

        try{
            byte[] encoded = Base64.encode(out_str.getBytes("UTF8"),Base64.DEFAULT);
            byte[] result = new byte[encoded.length+4];
            result[0] = 8;
            result[1] = 7;
            result[2] = 5;
            result[3] = 10;
            System.arraycopy(encoded,0,result,4,encoded.length);
            FileOutputStream f = new FileOutputStream(Environment.getExternalStorageDirectory()+"/inv.idb");
            f.write(result);
        } catch (IOException e)
        {
            AlertDialog a = new AlertDialog.Builder(this).setTitle("abc").setMessage("Error: "+e.toString()).show();

        }

    }
    public void expand_table(View vw)
    {

        String[] new_itm_nme = new String[itm_nme.length+1];
        System.arraycopy(itm_nme,0,new_itm_nme,0,itm_nme.length);
        itm_nme = new_itm_nme;
        int[] new_itm_quant = new int[itm_quant.length+1];
        System.arraycopy(itm_quant,0,new_itm_quant,0,itm_quant.length);
        itm_quant = new_itm_quant;
        EditText itmtxt = (EditText)findViewById(R.id.editText);
        itmtxt.setHint("Item number/"+itm_quant.length);
    }

    public void shrink_table(View vw)
    {
        String[] new_itm_nme = new String[itm_nme.length-1];
        int indx = 0;
        for(int i = 0; i < itm_nme.length;i++)
        {
            if(i!=cur_itm)
            {
                new_itm_nme[indx] = itm_nme[i];
                indx++;
            }
        }
        itm_nme = new_itm_nme;
        int[] new_itm_quant = new int[itm_quant.length-1];
        indx = 0;
        for(int i = 0; i < itm_quant.length; i++)
        {
            if(i!=cur_itm)
            {
                new_itm_quant[indx] = itm_quant[i];
                indx++;
            }
        }
        itm_quant = new_itm_quant;
        EditText itmtxt = (EditText)findViewById(R.id.editText);
        itmtxt.setHint("Item number/"+itm_quant.length);
    }
}
