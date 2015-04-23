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
            string[] to_write = { (Convert.ToString(numericUpDown1.Value)), textBox1.Text, (Convert.ToString(numericUpDown2.Value)), textBox2.Text, (Convert.ToString(numericUpDown3.Value)), textBox3.Text, (Convert.ToString(numericUpDown4.Value)), textBox4.Text, (Convert.ToString(numericUpDown5.Value)), textBox5.Text, (Convert.ToString(numericUpDown6.Value)), textBox6.Text, (Convert.ToString(numericUpDown7.Value)), textBox7.Text, (Convert.ToString(numericUpDown8.Value)), textBox8.Text, (Convert.ToString(numericUpDown9.Value)),textBox9.Text, (Convert.ToString(numericUpDown10.Value)),textBox10.Text,(Convert.ToString(numericUpDown11.Value)),textBox11.Text};
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
