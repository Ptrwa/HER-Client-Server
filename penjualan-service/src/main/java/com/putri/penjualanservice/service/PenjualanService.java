/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.putri.penjualanservice.service;

import com.putri.penjualanservice.entity.Penjualan;
import com.putri.penjualanservice.repository.PenjualanRepository;
import com.putri.penjualanservice.vo.Pelanggan;
import com.putri.penjualanservice.vo.ResponseTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 *
 * @author ASUS
 */
@Service
public class PenjualanService {
    
    @Autowired
    private PenjualanRepository penjualanRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Penjualan savePenjualan(Penjualan penjualan) {
        double total = 0.0;
        double harga = penjualan.getHarga();
        double jumlah = penjualan.getJumlah();
        total = harga*jumlah;
        
        
        double tDiskon = 0.0;
        double diskonPersen = penjualan.getDiskonPersen();
        tDiskon = diskonPersen*total;
        penjualan.setTotal(total);
        
        double Tppn = 0.0;
        double ppnPersen = penjualan.getppnPersen();
        Tppn = ppnPersen*(total-tDiskon);
        
        double TotalBayar = 0.0;
        double totalBayar = penjualan.gettotalBayar();
        totalBayar = total - tDiskon + Tppn;
        
        
        penjualan.setTDiskon(tDiskon);
        return penjualanRepository.save(penjualan);
    }

    public ResponseTemplateVO getPenjualan(Long penjualanId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Penjualan penjualan = penjualanRepository.findByPenjualanId(penjualanId);
//        Pelanggan pelanggan = restTemplate.getForObject("http://localhost:8888/pelangggan/" 
//              + penjualan.getPelangganId(), Pelanggan.class);
//        vo.setPelanggan(pelanggan);
        Pelanggan pelanggan = restTemplate.getForObject("http://localhost:8888/pelanggan/"
                + penjualan.getPelangganId(), Pelanggan.class);
        vo.setPenjualan(penjualan);
        vo.setPelanggan(pelanggan);
        return vo;
    }
}
