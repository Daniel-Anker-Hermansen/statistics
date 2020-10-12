package org.worldcubeassociation.statistics.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.controller.response.ResultSetResponse;
import org.worldcubeassociation.statistics.rowmapper.ResultSetRowMapper;
import org.worldcubeassociation.statistics.service.DatabaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatabaseServiceImpl implements DatabaseService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ResultSetRowMapper resultSetRowMapper;

	@Override
	public ResultSetResponse getResultSet(String query) {
		log.info("Execute query\n{}", query);

		ResultSetResponse resultSetResponse = new ResultSetResponse();

		List<LinkedHashMap<String, String>> sqlResult = jdbcTemplate.query(query, resultSetRowMapper);

		if (sqlResult.isEmpty()) {
			resultSetResponse.setContent(new ArrayList<>());
			resultSetResponse.setHeaders(new ArrayList<>());
			return resultSetResponse;
		}

		// Since we are using LinkedHashMap, the headers should be the same accross
		// every line of the content.
		// We take the first one.
		List<String> headers = sqlResult.get(0).entrySet().stream().map(entry -> entry.getKey())
				.collect(Collectors.toList());

		List<List<String>> content = new ArrayList<>();
		sqlResult.forEach(hash -> {
			List<String> list = new ArrayList<>();
			hash.entrySet().forEach(entry -> list.add(entry.getValue()));
			content.add(list);
		});
		resultSetResponse.setHeaders(headers);
		resultSetResponse.setContent(content);

		return resultSetResponse;
	}

}
