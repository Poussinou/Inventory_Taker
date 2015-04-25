using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Inventory_app
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string[] to_write = { (Convert.ToString(numericUpDown1.Value)), textBox1.Text, (Convert.ToString(numericUpDown2.Value)), textBox2.Text, (Convert.ToString(numericUpDown3.Value)), textBox3.Text, (Convert.ToString(numericUpDown4.Value)), textBox4.Text, (Convert.ToString(numericUpDown5.Value)), textBox5.Text, (Convert.ToString(numericUpDown6.Value)), textBox6.Text, (Convert.ToString(numericUpDown7.Value)), textBox7.Text, (Convert.ToString(numericUpDown8.Value)), textBox8.Text, (Convert.ToString(numericUpDown9.Value)),textBox9.Text, (Convert.ToString(numericUpDown10.Value)),textBox10.Text,(Convert.ToString(numericUpDown11.Value)),textBox11.Text, (Convert.ToString(numericUpDown12.Value)),textBox13.Text,(Convert.ToString(numericUpDown13.Value)),textBox14.Text,(Convert.ToString(numericUpDown14.Value)),textBox15.Text,Convert.ToString(numericUpDown15.Value),textBox16.Text,Convert.ToString(numericUpDown16.Value),textBox17.Text,Convert.ToString(numericUpDown17.Value),textBox18.Text,Convert.ToString(numericUpDown18.Value),textBox19.Text,Convert.ToString(numericUpDown19.Value),textBox20.Text,Convert.ToString(numericUpDown20.Value),textBox21.Text,Convert.ToString(numericUpDown21.Value),textBox22.Text,Convert.ToString(numericUpDown22.Value),textBox23.Text};
            System.IO.File.WriteAllLines(@textBox12.Text, to_write);
            return;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            string[] to_read;
            try
            {
                to_read = System.IO.File.ReadAllLines(@textBox12.Text);
            }
            catch(System.IO.FileNotFoundException)
            {
                return;
            }
            try
            {
                numericUpDown1.Value = Convert.ToInt32(to_read[0]);
                textBox1.Text = to_read[1];
                numericUpDown2.Value = Convert.ToInt32(to_read[2]);
                textBox2.Text = to_read[3];
                numericUpDown3.Value = Convert.ToInt32(to_read[4]);
                textBox3.Text = to_read[5];
                numericUpDown4.Value = Convert.ToInt32(to_read[6]);
                textBox4.Text = to_read[7];
                numericUpDown5.Value = Convert.ToInt32(to_read[8]);
                textBox5.Text = to_read[9];
                numericUpDown6.Value = Convert.ToInt32(to_read[10]);
                textBox6.Text = to_read[11];
                numericUpDown7.Value = Convert.ToInt32(to_read[12]);
                textBox7.Text = to_read[13];
                numericUpDown8.Value = Convert.ToInt32(to_read[14]);
                textBox8.Text = to_read[15];
                numericUpDown9.Value = Convert.ToInt32(to_read[16]);
                textBox9.Text = to_read[17];
                numericUpDown10.Value = Convert.ToInt32(to_read[18]);
                textBox10.Text = to_read[19];
                numericUpDown11.Value = Convert.ToInt32(to_read[20]);
                textBox11.Text = to_read[21];
                numericUpDown12.Value = Convert.ToInt32(to_read[22]);
                textBox13.Text = to_read[23];
                numericUpDown13.Value = Convert.ToInt32(to_read[24]);
                textBox14.Text = to_read[25];
                numericUpDown14.Value = Convert.ToInt32(to_read[26]);
                textBox15.Text = to_read[27];
                numericUpDown15.Value = Convert.ToInt32(to_read[28]);
                textBox16.Text = to_read[29];
                numericUpDown16.Value = Convert.ToInt32(to_read[30]);
                textBox17.Text = to_read[31];
                numericUpDown17.Value = Convert.ToInt32(to_read[32]);
                textBox18.Text = to_read[33];
                numericUpDown18.Value = Convert.ToInt32(to_read[34]);
                textBox19.Text = to_read[35];
                numericUpDown19.Value = Convert.ToInt32(to_read[36]);
                textBox20.Text = to_read[37];
                numericUpDown20.Value = Convert.ToInt32(to_read[38]);
                textBox21.Text = to_read[39];
                numericUpDown21.Value = Convert.ToInt32(to_read[40]);
                textBox22.Text = to_read[41];
                numericUpDown22.Value = Convert.ToInt32(to_read[42]);
                textBox23.Text = to_read[43];
            }
            catch(IndexOutOfRangeException)
            {
                return;
            }
            return;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            AboutBox1 box = new AboutBox1();
            box.ShowDialog();
        }
    }
}
