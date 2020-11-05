package edu.sjsu.ServerController.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import edu.sjsu.ServerController.service.InfosService;
import edu.sjsu.ServerController.service.KubernetesGetallNodesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.sjsu.ServerController.entity.ApiInfoPod;
import edu.sjsu.ServerController.entity.ApiInfoVm;
import edu.sjsu.ServerController.entity.PodInfo;
import edu.sjsu.ServerController.entity.RecentPodPerNode;

import org.springframework.ui.ModelMap;

@Controller
public class InfosController {
	
	@Autowired
	private InfosService service;
	
	@RequestMapping(method = RequestMethod.GET, value="/{DbName}/{MetricType}/{HostName}")
	  
	@ResponseBody
	public <T> List<T> findAllInfos(@PathVariable("HostName") String HostName,@PathVariable("DbName") String DbName,@PathVariable("MetricType") String MetricType) {

       return  service.getInfos(DbName,HostName,MetricType);
        
    }
	

	
	@Autowired
	private KubernetesGetallNodesService Kservice;
	
	@RequestMapping(method = RequestMethod.GET, value="/nodes")
	  
	@ResponseBody
	public String[] findAllNodes() {
		try {
			String res = Kservice.getallnodes();
			
			return  res.split("\\r?\\n");
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
			
		}
        
    }
	
	@RequestMapping(method = RequestMethod.GET, value="/pods")
	  
	@ResponseBody
	public ModelAndView findAllPods() {

		List<PodInfo> pods=new ArrayList<>();
		try {
			String res[] = Kservice.getallpods().split("\\r?\\n");
			
			for (int i =0;i<res.length;i++)
			{
				PodInfo p = new PodInfo();
				String val[] =res[i].split("\\r?\\t");

				p.setNodeName(val[1]);
				p.setPodName(val[0]);
				pods.add(p);
			}
			ModelAndView model = new ModelAndView("chart4");
			model.addObject("lists",pods );

	
			return model;

		}catch(Exception e) {
			e.printStackTrace();
			return null;
	
		}
        
    }
	@RequestMapping(method = RequestMethod.GET, value="/recentpods")
	  
	@ResponseBody
	public List<RecentPodPerNode> findAllRecentPods() {
		List<RecentPodPerNode> rp=new ArrayList<>();
		try {
			String res[] = Kservice.getallrecentpods().split("\\r?\\n");
			HashMap<String, Integer> map  = new HashMap<>(); 
			for (int i =0;i<res.length;i++)
			{
				
				String val[] =res[i].split("\\r?\\t");
				if (map.containsKey(val[1])){
					map.put(val[1],map.get(val[1])+1);
				}else {
					map.put(val[1], 1);
				}
			}
			for (Entry<String, Integer> entry : map.entrySet()) { 
				RecentPodPerNode recentpod=new RecentPodPerNode();
				recentpod.setY(entry.getValue());
				recentpod.setLabel(entry.getKey());
				rp.add(recentpod);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
			
		}
		
		return rp;
        
    }
	
	
	@RequestMapping(method = RequestMethod.GET, value="/recentfailedpods")
	  
	@ResponseBody
	public List<RecentPodPerNode> findAllRecentFailedPods() {
		List<RecentPodPerNode> rp=new ArrayList<>();
		try {
			String res[] = Kservice.getallrecentfailedpods().split("\\r?\\n");
			HashMap<String, Integer> map  = new HashMap<>(); 
			for (int i =0;i<res.length;i++)
			{
				
				String val[] =res[i].split("\\r?\\t");
				if (map.containsKey(val[1])){
					map.put(val[1],map.get(val[1])+1);
				}else {
					map.put(val[1], 1);
				}
			}
			for (Entry<String, Integer> entry : map.entrySet()) { 
				RecentPodPerNode recentpod=new RecentPodPerNode();
				recentpod.setY(entry.getValue());
				recentpod.setLabel(entry.getKey());
				rp.add(recentpod);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
			
		}
		
		return rp;
        
    }
	
	
	

	
	 
		@RequestMapping(method = RequestMethod.GET,value="/plotgraphforPod/{DbName}/{MetricType}/{PodName}")
	
		public String springMVC1(ModelMap modelMap,@PathVariable("PodName") String PodName,@PathVariable("DbName") String DbName,@PathVariable("MetricType") String MetricType) {
		ApiInfoPod a= new ApiInfoPod();
		a.setDbName(DbName);
		a.setPodName(PodName);
		a.setMetricType(MetricType);
		
	
		modelMap.addAttribute("Info", a);
			return "chart";
		}
		@RequestMapping(method = RequestMethod.GET,value="/plotgraphforVm/{DbName}/{MetricType}/{VmName}")
		
		public String springMVC2(ModelMap modelMap,@PathVariable("VmName") String VmName,@PathVariable("DbName") String DbName,@PathVariable("MetricType") String MetricType) {
		ApiInfoVm a= new ApiInfoVm();
		a.setDbName(DbName);
		a.setVmName(VmName);
		a.setMetricType(MetricType);
		
	
		modelMap.addAttribute("Info", a);
			return "chart2";
		}
	
	
		@RequestMapping(method = RequestMethod.GET,value="/plotRecentPodsPerVm")
		public String springMVC3(ModelMap modelMap) {
			return "chart3";
			
		}
		
		@RequestMapping(method = RequestMethod.GET,value="/plotRecentFailedPodsPerVm")
		public String springMVC4(ModelMap modelMap) {
			return "chart5";
			
		}
	
}
