package com.javaproject.rasahotel.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.javaproject.rasahotel.entities.BookedRoom;
import com.javaproject.rasahotel.entities.Category;
import com.javaproject.rasahotel.entities.Customer;
import com.javaproject.rasahotel.entities.Room;
import com.javaproject.rasahotel.services.OptimizeTableService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SchedulerService {
    
    @Autowired
    OptimizeTableService optimizeTableService;
    
    // detik menit jam hari bulan tahun
    @Scheduled(cron = "* * */1 * * *")
    public void optimizeTableDaily(){
        try {

            log.info("Running Optimization Table Query...");
            // Digunakan untuk mengoptimalkan tabel dan mengurangi fragmentasi data agar tidak menghambat jalannya query dan kodingan
            optimizeTableService.optimizeTable(optimizeTableService.getTableName(BookedRoom.class));
            optimizeTableService.optimizeTable(optimizeTableService.getTableName(Category.class));
            optimizeTableService.optimizeTable(optimizeTableService.getTableName(Customer.class));
            optimizeTableService.optimizeTable(optimizeTableService.getTableName(Room.class));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
