package qlks.BUS;


import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.SuDungDichVuDAO;
import qlks.DTO.DichVu;
import qlks.DTO.DichVuDTO;
import qlks.DTO.HopDongThuePhong;
import qlks.DTO.HopDongThuePhongDTO;
import qlks.DTO.MatHang;
import qlks.DTO.MatHangDTO;
import qlks.DTO.SuDungDichVu;
import qlks.DTO.SuDungDichVuDTO;
import qlks.GUI.nhaphang;


public class SuDungDichVuBUS {
    private SuDungDichVuDAO sddvDAO;
    
    public SuDungDichVuBUS(){
        sddvDAO = new SuDungDichVuDAO();
    }
    
    public ArrayList<SuDungDichVu> docDanhSachSuDungDichVu(){
        return sddvDAO.docDanhSachSuDungDichVu();
    }
    
    public boolean themSuDungDichVu(SuDungDichVuDTO sddvDTO, SuDungDichVu sddv){
        //Vua la khoa ngoai vua la khoa chinh
        
        //Kiem tra co bi trung So hop dong va ma dich vu
        for(SuDungDichVu sddvt: sddvDTO.docDanhSachSuDungDichVu()){
            if(sddvt.getSoHopDongTHuePhong().compareTo(sddv.getSoHopDongTHuePhong())==0&&
                    sddvt.getMaDichVu().compareTo(sddv.getMaDichVu())==0){
                //Neu da ton tai thi thong bao
                JOptionPane.showMessageDialog(null, "Đang sử dụng dịch vụ đề nghị tăng số lượng");
                return false;
            }
        }
        
        DichVuDTO nvDTO = new DichVuDTO();
        HopDongThuePhongDTO hdtpDTO = new HopDongThuePhongDTO();
        boolean mdv = false; // Ma dich vu
        boolean shdtp = false; //So hop dong thue phong
        
        //Tim ma dich vu
        for(DichVu dv: nvDTO.docDanhSachDichVu()){
            if(dv.getMaDichVu().compareTo(sddv.getMaDichVu())==0){
                mdv = true;
            }
        }
        
        //Tim so hop dong thue phong
        for(HopDongThuePhong hdtp: hdtpDTO.docDanhSachHopDongThuePhong()){
            if(hdtp.getSoHopDongThuePhong().compareTo(sddv.getSoHopDongTHuePhong())==0){
                shdtp = true;
            }
        }
        
        //Bao loi new Ma dich vu ko ton tai
        if(mdv==false){
            JOptionPane.showMessageDialog(null, "Mã dịch vụ chưa tồn tại");
            return false;
        }
        //Bao loi neu So hop dong thue phong ko ton tai
        if(shdtp==false){
            JOptionPane.showMessageDialog(null, "Số hợp đồng chưa tồn tại");
            return false;
        }
        // khi them dich vu moi neu o mat hang (kho hang) khong du so luong thi bao loi
        // Dich vu co the lay mat hang (trong kho hang) hoac ko
        for(DichVu dv: new DichVuDTO().docDanhSachDichVu()){
            //Noi dich vu va su dung dich vu de lay cot Mat Hang (Trong dich vu)
            if(dv.getMaDichVu().compareTo(sddv.getMaDichVu())==0){
                for(MatHang mh: new MatHangDTO().docDanhSachMatHang()){
                    //Noi dich vu voi mat hang
                    if(dv.getMatHang().compareTo(mh.getMaMatHang())==0){
                        // so luong mat hang ko du
                        if(mh.getSoLuong()<sddv.getSoLuong()){
                            JOptionPane.showConfirmDialog(null, "Số lượng mặt hàng không đủ");
                            return false;
                        }
                        
                        // Du thi tru di so luong trong mat hang (Kho)
                        mh.setSoLuong(mh.getSoLuong()-sddv.getSoLuong());
                        MatHangBUS mhBUS = new MatHangBUS();
                        mhBUS.suaSoLuong(mh);
                        nhaphang.mhDTO.suaSoLuong(mh);
                        break;
                    }
                }
                break;
            }
        }
        //Them trong DAO va DTO
        sddvDAO.themSuDungDichVu(sddv);
        sddvDTO.themSuDungDichVu(sddv);
        return true;
    }
    
    public boolean suaSuDungDichVu(SuDungDichVuDTO sddvDTO,SuDungDichVu sddv){
        int row = -1;
        //Tim xem da co su dung dich vu hay chua
        for(int i=0;i<sddvDTO.docDanhSachSuDungDichVu().size();i++){
            if(sddv.getSoHopDongTHuePhong().compareTo(sddvDTO.docDanhSachSuDungDichVu().get(i).getSoHopDongTHuePhong())==0&&
                    sddv.getMaDichVu().compareTo(sddvDTO.docDanhSachSuDungDichVu().get(i).getMaDichVu())==0){
                row = i;
                break;
            }
        }
        //Bao loi neu chua
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại số hợp đồng và mã dịch vụ");
            return false;
        }
        // Neu ko co gi thay doi thi ko can sua
        if(sddv.getSoHopDongTHuePhong().compareTo(sddvDTO.docDanhSachSuDungDichVu().get(row).getSoHopDongTHuePhong())==0 &&
                sddv.getMaDichVu().compareTo(sddvDTO.docDanhSachSuDungDichVu().get(row).getMaDichVu())==0 &&
                sddv.getDonGia()==sddvDTO.docDanhSachSuDungDichVu().get(row).getDonGia() &&
                sddv.getSoLuong()==sddvDTO.docDanhSachSuDungDichVu().get(row).getSoLuong() &&
                sddv.getThanhTien()==sddvDTO.docDanhSachSuDungDichVu().get(row).getThanhTien()){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        //Sua trong DAO va DTO
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa");
            if(chon==JOptionPane.YES_OPTION){
                sddvDAO.suaSuDungDichVu(sddv);
                sddvDTO.suaSuDungDichVu(sddv, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaSuDungDichVu(SuDungDichVuDTO sddvDTO,SuDungDichVu sddv){
        int row = -1;
        //Tim xem da su dung dich vu hay chua
        for(int i=0;i<sddvDTO.docDanhSachSuDungDichVu().size();i++){
            if(sddv.getSoHopDongTHuePhong().compareTo(sddvDTO.docDanhSachSuDungDichVu().get(i).getSoHopDongTHuePhong())==0 &&
                    sddv.getMaDichVu().compareTo(sddvDTO.docDanhSachSuDungDichVu().get(i).getMaDichVu())==0){
                row = i;
                break;
            }
        }
        //bao loi neu chua
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại số hợp đồng và mã dịch vụ");
            return false;
        }
        //Xoa luon ko giu lai vi la vua la khoa ngoai vua la khoa chinh
        if(JOptionPane.showConfirmDialog(null, "Bạn muốn xóa")==JOptionPane.YES_OPTION){
            sddvDAO.xoaSuDungDichVu(sddv.getSoHopDongTHuePhong(), sddv.getMaDichVu());
            sddvDTO.xoaSuDungDichVu(row);
            return true;
        }
        return false;
    }
}
