package club.plus1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // КОНТЕКСТНОЕ МЕНЮ

    // Регистрируем контекстное меню
    @Override
    protected void onResume(){
        TextView text = this.findViewById(R.id.textView);
        registerForContextMenu(text);
        super.onResume();
    }

    // Отключаем контекстное меню
    @Override
    protected void onPause(){
        TextView text = this.findViewById(R.id.textView);
        unregisterForContextMenu(text);
        super.onPause();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info){
        if(v.getId() == R.id.textView){
            // Можно заполнить динамически, а можно через inflater и макет
            menu.add(Menu.NONE, 1, Menu.NONE, "Контекст");
            menu.add(Menu.NONE, 2, Menu.NONE, "2й пункт");
        } else {
            super.onCreateContextMenu(menu, v, info);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 1:
                Toast.makeText(this, "Выполнен контекст!", Toast.LENGTH_LONG).show();
                return true;
            case 2:
                Toast.makeText(this, "Выполнен 2й пункт!", Toast.LENGTH_LONG).show();
                return true;
            default:
                super.onContextItemSelected(item);
        }
        return true;
    }

    // ОБЫЧНОЕ МЕНЮ

    // Создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Обработка пунктов меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.settings:
                showDialog();
                return true;
            case R.id.check:
                item.setChecked(!item.isChecked());
                Toast.makeText(this, "Выполнено!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.other:
                showToast();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Изменение состава меню перед показом
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem checkItem = menu.findItem(R.id.check);
        menu.findItem(R.id.other).setVisible(checkItem.isChecked());
        return super.onPrepareOptionsMenu(menu);
    }

    // ДИАЛОГ

    // Показ диалога
    public void onClick(View v){
        showDialog();
    }

    // Подготовка и показ диалога
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вопрос к знатокам")
                .setMessage("Москва - столица России?")
                .setPositiveButton("Да!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast();
                    }
                })
                .setNegativeButton("Не знаю.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    // ПРОДВИНУТЫЙ ТОСТ

    // Подготовка и показ тоста
    public void showToast(){
        // Подготовка тоста
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams")
        View layout = inflater.inflate(R.layout.toast, null);
        // Заполнение элементов тоста
        TextView text = layout.findViewById(R.id.textView2);
        text.setText("УРАААА!");
        ImageView image = layout.findViewById(R.id.imageView);
        image.setImageResource(R.mipmap.ic_launcher);
        // Показ тоста
        Toast toast = new Toast(this);
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }

}
