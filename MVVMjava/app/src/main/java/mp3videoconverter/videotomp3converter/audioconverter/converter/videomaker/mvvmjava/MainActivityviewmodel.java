package mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.mvvmjava;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityviewmodel extends ViewModel {


    public MutableLiveData<String> text= new MutableLiveData<>();




    public void showtext(){
        String name="komal";
        text.setValue(name);
    }
}
