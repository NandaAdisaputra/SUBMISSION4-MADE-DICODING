package com.nandaadisaputra.cataloguemovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nandaadisaputra.cataloguemovie.model.movie.ResponseMovie;
import com.nandaadisaputra.cataloguemovie.model.tv.ResponseTv;
import com.nandaadisaputra.cataloguemovie.repository.MovieRepository;
import com.nandaadisaputra.cataloguemovie.repository.TvRepository;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ResponseMovie> responseMovieMutableLiveData;

    public void initMovie() {
        if (responseMovieMutableLiveData != null) {
            return;
        }
        MovieRepository movieRepository = MovieRepository.getInstancemovie();
        responseMovieMutableLiveData = movieRepository.getMovie();
    }

    public LiveData<ResponseMovie> getMovieModel() {
        return responseMovieMutableLiveData;
    }



    public void initSearchMovie(String text){
        if (responseMovieMutableLiveData != null){
            return;
        }
        MovieRepository movieRepo = MovieRepository.getInstancemovie();
        responseMovieMutableLiveData = movieRepo.searchMovie(text);
    }
    public LiveData<ResponseMovie> searchMovie(){
        return responseMovieMutableLiveData;
    }

    private MutableLiveData<ResponseTv> responseTvMutableLiveData;

    public void initTv() {
        if (responseTvMutableLiveData != null) {
            return;
        }
        TvRepository tvRepository = TvRepository.getInstance();
        responseTvMutableLiveData = tvRepository.getTV();
    }

    public LiveData<ResponseTv> getTvModel() {
        return responseTvMutableLiveData;
    }
}
