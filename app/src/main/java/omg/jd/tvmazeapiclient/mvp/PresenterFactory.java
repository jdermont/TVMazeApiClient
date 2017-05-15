package omg.jd.tvmazeapiclient.mvp;

public interface PresenterFactory<T extends BasePresenter> {
    T create();
}