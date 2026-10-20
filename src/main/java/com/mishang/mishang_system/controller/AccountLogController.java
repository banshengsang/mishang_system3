package com.mishang.mishang_system.controller;

import com.mishang.mishang_system.entity.AccountLog;
import com.mishang.mishang_system.service.IAccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 账户流水表 前端控制器
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@Controller
@RequestMapping("/accountLog")
public class AccountLogController {

    @Autowired
    private IAccountLogService iaccountLogService;
    @GetMapping("/list/{customerId}")
    public List<AccountLog> listByCustomerId(@PathVariable Integer customerId) {
        return iaccountLogService.lambdaQuery()
                .eq(AccountLog::getCustomerId, customerId)
                .orderByDesc(AccountLog::getLogTime) // 按时间倒序，最近的在前面
                .list();
    }

    @PostMapping("/add")
    public String add (@RequestBody AccountLog accountLog){
        iaccountLogService.save(accountLog);
        return "新增成功";
    }
    @PutMapping("/update")
    public String update(@RequestBody AccountLog accountLog){
        iaccountLogService.updateById(accountLog);
        return "更新成功";
    }
    @DeleteMapping("/delete(id)")
    public String delete(@RequestBody AccountLog accountLog){
        iaccountLogService.removeById(accountLog);
        return "删除成功";
    }

}
