package com.grice.util;

import com.blade.kit.IOKit;
import com.grice.model.Node;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public final class GriceUtil {

    public static Node getNodeDoc(String path) {
        BufferedReader br = null;
        try {
            if (!Files.exists(Paths.get(path))) {
                return null;
            }

            File file = new File(path);

            Node doc = new Node();
            doc.setPath(file.getPath());
            doc.setName(file.getName().replace(".md", ""));
            br = new BufferedReader(new FileReader(file));

            String  line;
            boolean isContent = false;
            int     count     = 0;

            StringBuilder content = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.contains("---")) {
                    count++;
                    continue;
                }
                if (count > 1 && line.contains("{:toc}")) {
                    isContent = true;
                    continue;
                }
                if (!isContent && line.contains("title")) {
                    int pos = line.indexOf(":");
                    doc.setTitle(line.substring(pos + 1).trim());
                    continue;
                }
                if (!isContent && line.contains("sort")) {
                    int pos = line.indexOf(":");
                    doc.setSort(Integer.valueOf(line.substring(pos + 1).trim()));
                    continue;
                }
                if (!isContent && line.contains("plain")) {
                    int pos = line.indexOf(":");
                    doc.setPlain(Boolean.valueOf(line.substring(pos + 1).trim()));
                    continue;
                }
                if (!isContent && line.contains("root")) {
                    int pos = line.indexOf(":");
                    doc.setRoot(Boolean.valueOf(line.substring(pos + 1).trim()));
                    continue;
                }
                if (isContent) {
                    content.append(line).append("\r\n");
                }
            }
            doc.setContent(content.toString());
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOKit.closeQuietly(br);
        }
        return null;
    }

    public static Map<String, String> load(String path) {
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(Objects.requireNonNull(
                    GriceUtil.class.getClassLoader().getResourceAsStream(path)), StandardCharsets.UTF_8)
            );

            Map<String, String> map = new HashMap<>(props.size());

            Set<Map.Entry<Object, Object>> set = props.entrySet();

            if(set.isEmpty()){
                return map;
            }

            for (Map.Entry<Object, Object> entry : set) {
                String key   = entry.getKey().toString();
                String value = entry.getValue().toString().trim();
                String fuKey = getWildcard(value);
                if (null != fuKey && null != props.get(fuKey)) {
                    String fuValue = props.get(fuKey).toString();
                    value = value.replaceAll("\\$\\{" + fuKey + "\\}", fuValue);
                }
                map.put(key, value);
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getWildcard(String str) {
        if (null != str && str.contains("${")) {
            int start = str.indexOf("${");
            int end   = str.indexOf("}");
            if (start != -1 && end != -1) {
                return str.substring(start + 2, end);
            }
        }
        return null;
    }

}
