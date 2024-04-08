package datdvph44632.fpoly.assignment_networking.models;

import java.util.Date;
import java.util.List;

public class Truyen {
    private String _id;
    private String ten_truyen;
    private String mo_ta;
    private String tac_gia;
    private String nam_xb;
    private String anh_bia;
    private List<String> noi_dung_anh_truyen;
    private List<BinhLuan> binh_luan;

    public Truyen() {
    }

    public Truyen(String _id, String ten_truyen, String mo_ta, String tac_gia, String nam_xb, String anh_bia, List<String> noi_dung_anh_truyen, List<BinhLuan> binh_luan) {
        this._id = _id;
        this.ten_truyen = ten_truyen;
        this.mo_ta = mo_ta;
        this.tac_gia = tac_gia;
        this.nam_xb = nam_xb;
        this.anh_bia = anh_bia;
        this.noi_dung_anh_truyen = noi_dung_anh_truyen;
        this.binh_luan = binh_luan;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTen_truyen() {
        return ten_truyen;
    }

    public void setTen_truyen(String ten_truyen) {
        this.ten_truyen = ten_truyen;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getTac_gia() {
        return tac_gia;
    }

    public void setTac_gia(String tac_gia) {
        this.tac_gia = tac_gia;
    }

    public String getNam_xb() {
        return nam_xb;
    }

    public void setNam_xb(String nam_xb) {
        this.nam_xb = nam_xb;
    }

    public String getAnh_bia() {
        return anh_bia;
    }

    public void setAnh_bia(String anh_bia) {
        this.anh_bia = anh_bia;
    }

    public List<String> getNoi_dung_anh_truyen() {
        return noi_dung_anh_truyen;
    }

    public void setNoi_dung_anh_truyen(List<String> noi_dung_anh_truyen) {
        this.noi_dung_anh_truyen = noi_dung_anh_truyen;
    }

    public List<BinhLuan> getBinh_luan() {
        return binh_luan;
    }

    public void setBinh_luan(List<BinhLuan> binh_luan) {
        this.binh_luan = binh_luan;
    }
}
