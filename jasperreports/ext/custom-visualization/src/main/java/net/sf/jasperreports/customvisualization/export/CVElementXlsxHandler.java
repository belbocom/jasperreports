/*******************************************************************************
 * Copyright (C) 2005 - 2016 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * 
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 * 
 * The Custom Visualization Component program and the accompanying materials
 * has been dual licensed under the the following licenses:
 * 
 * Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Custom Visualization Component is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package net.sf.jasperreports.customvisualization.export;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.ooxml.GenericElementXlsxHandler;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporterContext;

/**
 * @author Giulio Toffoli (gtoffoli@tibco.com)
 */
public class CVElementXlsxHandler implements GenericElementXlsxHandler
{
	private static final CVElementXlsxHandler INSTANCE = new CVElementXlsxHandler();
	private static final Log log = LogFactory.getLog(CVElementXlsxHandler.class);

	public static CVElementXlsxHandler getInstance()
	{
		return INSTANCE;
	}

	@Override
	public boolean toExport(JRGenericPrintElement element)
	{
		return true;
	}

	@Override
	public void exportElement(
		JRXlsxExporterContext exporterContext,
		JRGenericPrintElement element,
		JRExporterGridCell gridCell,
		int colIndex,
		int rowIndex
		) throws JRException
	{
		if (log.isDebugEnabled())
		{
			log.debug("Exporting to XLSX " + element);
		}

		try
		{
			JRPrintImage chartImage = 
				CVElementImageProvider.getDefaultProvider()
					.getImage(exporterContext.getJasperReportsContext(), element, false);
			JRXlsxExporter exporter = (JRXlsxExporter) exporterContext.getExporterRef();
			exporter.exportImage(chartImage, gridCell, colIndex, rowIndex, 0, 0, null);// TODO lucianc is this OK?
		}
		catch (Exception e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public JRPrintImage getImage(JRXlsxExporterContext jrxec, JRGenericPrintElement jrgpe) throws JRException
	{
		return CVElementImageProvider.getDefaultProvider().getImage(jrxec.getJasperReportsContext(), jrgpe, true);
	}
}
