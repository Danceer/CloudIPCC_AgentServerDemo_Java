package com.huawei.agentdemo.servlet;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.agentdemo.service.QueryCallDataService;
import com.huawei.demo.util.StringUtils;

public class QueryCallDataInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(QueryCallDataInfoServlet.class);

	public QueryCallDataInfoServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	{
        try
        {
            try 
            {
                request.setCharacterEncoding("utf-8");
            } 
            catch (UnsupportedEncodingException e1) 
            {
                LOG.error("request set character encoding error");
            }
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            String workNo = request.getParameter("identityMark");
            String type = request.getParameter("type");
            String callData = request.getParameter("callData");
            String callId = request.getParameter("callId");
            String result = StringUtils.EMPTY_STRING;
            
            if (StringUtils.isNullOrBlank(workNo))
            {
                LOG.warn("workNo is null or blank");
                return;
            }
            if (StringUtils.isNullOrBlank(type))
            {
                LOG.warn("type is null or blank");
                return;
            }
            switch(type)
            {
                case "HOLDLIST":
                    result = QueryCallDataService.queryHoldList(workNo);
                    break;
                case "CALLINFO":
                    result = QueryCallDataService.queryCallInfo(workNo);
                    break;
                case "SETCALLDATA":
                    if(StringUtils.isNullOrBlank(callData))
                    {
                        LOG.warn("callData is null or blank");
                        return;
                    }
                    if(StringUtils.isNullOrBlank(callId))
                    {
                        LOG.warn("callId is null or blank");
                        return;
                    }
                    result = QueryCallDataService.setCallData(workNo,callId,callData);
                    break;
                case "QUERYCALLDATA":
                    result = QueryCallDataService.queryCallData(workNo);
                    break;
                default:
                    break;  
            }
            
            PrintWriter out = null;
            try
            {
                out = response.getWriter();
                out.println(result);
            }
            catch(Exception e)
            {
                LOG.error("PrintWriter println Error");
            }
            finally
            {
                if(null != out)
                {
                    try
                    {
                        out.flush();
                        out.close();
                    }
                    catch(Exception e)
                    {
                        LOG.error("Close PrintWriter error");
                    }
                }
            }
        }
        catch(Throwable throwable)
        {
            LOG.error("catch throwable at QueryCallDataInfoServlet: ",throwable);
        }
        
	}
}
