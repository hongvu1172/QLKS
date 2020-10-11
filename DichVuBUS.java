
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.DichVuDAO;
import qlks.DTO.DichVu;
import qlks.DTO.DichVuDTO;
import qlks.DTO.MatHang;
import qlks.DTO.MatHangDTO;

public class DichVuBUS {
    private DichVuDAO dvDAO;
    
    public DichVuBUS(){
        dvDAO = new DichVuDAO();
    }
    
    public ArrayList<DichVu> docDanhSachDichVu(){
        return dvDAO.docDanhSachDichVu();
    }    

    public boolean themDichVu(DichVuDTO dvDTO, DichVu dv){
        for(int i=0; i<dvDTO.docDanhSachDichVu().size();i++){
            DichVu dvt = dvDTO.docDanhSachDichVu().get(i);
            if(dvt.getMaDichVu().compareTo(dv.getMaDichVu())==0){
                if(dvt.isXoa()){
                    if(JOptionPane.showConfirmDialog(null, "Phát hiện mã dịch vụ cũ bạn muốn ghi đè lên dịch vụ này")
                            ==JOptionPane.YES_OPTION){
                        //Khi ghi de thi kiem tra ma mat hang da ton tai hay chua
                        if(kiemTraMatHangTrongDichVu(dv)==false){
                            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã mặt hàng");
                            return false;
                        }
                        
                        dvDAO.suaDichVu(dv);
                        dvDTO.suaDichVu(dv, i);
                        return true;
                    }
                    return false;
                }
                JOptionPane.showMessageDialog(null, "Trùng mã dịch vụ.");
                return false;
            }
        }
        
        //Khi them dich vu neu co mat hang kiem tra da co mat hang chua
        if(kiemTraMatHangTrongDichVu(dv)==false){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã mặt hàng");
            return false;
        }
        
        dvDAO.themDichVu(dv);
        dvDTO.themDichVu(dv);
        return true;
    }
    
    //Kiem tra neu dich vu co mat hang ko thi thoi
    public boolean kiemTraMatHangTrongDichVu(DichVu dv){
        if(dv.getMatHang().length()>0){
            MatHangDTO mhDTO = new MatHangDTO();
            for(MatHang mh: mhDTO.docDanhSachMatHang()){
                if(mh.getMaMatHang().compareTo(dv.getMatHang())==0){
                    return true;
                }
            }
            return false;
        }
        //Neu dich vu ko co mat hang thi tra ve true
        return true;
    }
    
    public boolean suaDichVu(DichVuDTO dvDTO,DichVu dv){
        int row = -1;

        for(int i=0;i<dvDTO.docDanhSachDichVu().size();i++){
            if(dv.getMaDichVu().compareTo(dvDTO.docDanhSachDichVu().get(i).getMaDichVu())==0){
                row = i;
                break;
            }
        }
        if(row == -1 || dvDTO.docDanhSachDichVu().get(row).isXoa()){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã dịch vụ");
            return false;
        }
        
        //Khi sua dich vu neu co mat hang kiem tra da co mat hang chua
        if(kiemTraMatHangTrongDichVu(dv)==false){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã mặt hàng");
            return false;
        }

        if(dv.getMaDichVu().compareTo(dvDTO.docDanhSachDichVu().get(row).getMaDichVu())==0 &&
                dv.getTenDichVu().compareTo(dvDTO.docDanhSachDichVu().get(row).getTenDichVu())==0 &&
                dv.getDonGia()==dvDTO.docDanhSachDichVu().get(row).getDonGia() &&
                dv.getMatHang().compareTo(dvDTO.docDanhSachDichVu().get(row).getMatHang())==0){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa dịch vụ này");
            if(chon==JOptionPane.YES_OPTION){
                dvDAO.suaDichVu(dv);
                dvDTO.suaDichVu(dv, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaDichVu(DichVuDTO dvDTO, String mdv){
        int row = -1;
        for(int i=0;i<dvDTO.docDanhSachDichVu().size();i++){
            if(mdv.compareTo(dvDTO.docDanhSachDichVu().get(i).getMaDichVu())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã dịch vụ");
            return false;
        }
        
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa dịch vụ này");
        if(chon==JOptionPane.YES_OPTION){
            dvDAO.xoaDichVu(mdv);
            dvDTO.xoaDichVu(mdv, row);
            return true;
        }else{
            return false;
        }
    }
}
