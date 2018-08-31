package com.jdrx.platform.sample.dao;

import com.jdrx.platform.commons.rest.exception.BizException;
import com.jdrx.platform.commons.rest.utils.JsonMapper;
import com.jdrx.platform.jdbc.beans.vo.PageVO;
import com.jdrx.platform.sample.beans.dto.ServerInfoDTO;
import com.jdrx.platform.sample.beans.entry.ServerInfoPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class ServerInfoDAO {
    static final String COLLECTION_NAME="serverInfo";

    @Autowired
    private MongoTemplate mongoTemplate;

    public PageVO<ServerInfoPO> findBy(ServerInfoPO serverInfoPO,Integer pageNum,Integer pageSize) throws BizException {
        if (serverInfoPO.getIp() != null ) {
            try {
                Pattern pattern = Pattern.compile("^.*" + serverInfoPO.getIp() + ".*$");
                Query query = new Query(Criteria.where("ip").regex(pattern));
                long count = mongoTemplate.count(query, ServerInfoPO.class, COLLECTION_NAME);

                PageVO<ServerInfoPO> pageVO = new PageVO<>();
                pageVO.setTotal(count);
                Pageable pageable = new PageRequest(pageNum , pageSize);
                query.with(pageable);
                List<ServerInfoPO> serverInfoList = mongoTemplate.find(query, ServerInfoPO.class, COLLECTION_NAME);
                pageVO.setPageNum(pageNum);
                pageVO.setPageSize(pageSize);
                pageVO.setData(serverInfoList);
                return pageVO;
            } catch (Exception e) {
                throw new BizException("查询错误");
            }
        }
        if (serverInfoPO.getHost() != null ) {
            try {
                Pattern pattern = Pattern.compile("^.*" + serverInfoPO.getHost() + ".*$");
                Query query = new Query(Criteria.where("host").regex(pattern));
                long count = mongoTemplate.count(query, ServerInfoPO.class, COLLECTION_NAME);

                PageVO<ServerInfoPO> pageVO = new PageVO<>();
                pageVO.setTotal(count);
                Pageable pageable = new PageRequest(pageNum , pageSize);
                query.with(pageable);
                List<ServerInfoPO> serverInfoList = mongoTemplate.find(query, ServerInfoPO.class, COLLECTION_NAME);
                pageVO.setPageNum(pageNum);
                pageVO.setPageSize(pageSize);
                pageVO.setData(serverInfoList);
                return pageVO;
            } catch (Exception e) {
                throw new BizException("查询错误");
            }
        }
        return null;
    }

    public void saveList(List<String> serverInfoList) throws BizException {
        JsonMapper mapper = new JsonMapper();
        try {
            for (String string : serverInfoList) {
                ServerInfoPO serverInfoPO = mapper.readValue(string, ServerInfoPO.class);
                mongoTemplate.save(serverInfoPO,COLLECTION_NAME);
            }

        } catch (Exception e) {
            throw new BizException("插入失败");
        }

    }

    public void upsert(String serverInfoString) throws BizException {
        try {
            JsonMapper mapper = new JsonMapper();
            ServerInfoPO serverInfoPO = mapper.readValue(serverInfoString, ServerInfoPO.class);
            Query query = new Query(Criteria.where("ip").is(serverInfoPO.getIp()));
            Update update = new Update();
            Map<String,Object> serverMap = BeanMap.create(serverInfoPO);
            for (Map.Entry<String,Object> entry:serverMap.entrySet()) {
                if(entry.getValue()!=null){
                    update.set(entry.getKey(), entry.getValue());
                }
            }
            mongoTemplate.upsert(query, update, COLLECTION_NAME);
        } catch (Exception e) {
            throw new BizException("插入失败");
        }

    }

    public void save(ServerInfoPO serverInfoPO) throws BizException {
        try {
            mongoTemplate.save(serverInfoPO,COLLECTION_NAME);
        } catch (Exception e) {
            throw new BizException("插入失败");
        }

    }
    public PageVO<ServerInfoPO> findAll(Integer pageNum, Integer pageSize) throws BizException {
        try {
            PageVO<ServerInfoPO> pageVO = new PageVO<>();
            Query query = new Query();

            long count = mongoTemplate.count(query, COLLECTION_NAME);
            pageVO.setTotal(count);

            Pageable pageable = new PageRequest(pageNum , pageSize);
            query.with(pageable);
            List<ServerInfoPO> serverInfoList = mongoTemplate.find(query, ServerInfoPO.class, COLLECTION_NAME);
            pageVO.setPageNum(pageNum);
            pageVO.setPageSize(pageSize);
            pageVO.setData(serverInfoList);
            return pageVO;
        } catch (Exception e) {
            throw new BizException("查找失败");
        }
    }


}