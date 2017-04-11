package univ.lecture.riotapi.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import univ.lecture.riotapi.model.Summoner;

import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Created by tchi on 2017. 4. 1..
 */
@RestController
@RequestMapping("/api/v1")
@Log4j
public class RiotApiController {
	private static final String template = "%s is %f";
    @Autowired
    private RestTemplate restTemplate;

    @Value("${riot.api.endpoint}")
    private String riotApiEndpoint;

    @Value("${riot.api.key}")
    private String riotApiKey;

	@Value("${target.endpoint}")
	private String targetEndPoint;

	@RequestMapping(value="/calc/{exp}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Summoner querySummoner(@PathVariable("exp") String expression) throws UnsupportedEncodingException {

		Summoner summoner = new Summoner("hi", "there");

		try{
			URL url = new URL(targetEndPoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			String param = "{\"title\":\"sdsd\",\"body\":\"dsfdf\"}";
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

			osw.write(param);
			osw.flush();

			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			String line = null;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}

			osw.close();
			br.close();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(ProtocolException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}




		
        return summoner;

		/*
        final String url = serverIp + ":8080" + "/api/v1/calc/" + expression;

        String response = restTemplate.getForObject(url, String.class);
        Map<String, Object> parsedMap = new JacksonJsonParser().parseMap(response);

        parsedMap.forEach((key, value) -> log.info(String.format("key [%s] type [%s] value [%s]", key, value.getClass(), value)));

        Map<String, Object> summonerDetail = (Map<String, Object>) parsedMap.values().toArray()[0];
        String key = (String)summonerDetail.get("key");
        String value = (String)summonerDetail.get("value");
        Summoner summoner = new Summoner(key, value);
		*/
    }
}
