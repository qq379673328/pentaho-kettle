/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2014 Pentaho Corporation..  All rights reserved.
*/
package org.pentaho.di.monitor.job;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.extension.ExtensionPoint;
import org.pentaho.di.core.extension.ExtensionPointInterface;
import org.pentaho.di.job.Job;
import org.pentaho.di.monitor.IKettleMonitoringEvent;
import org.pentaho.di.monitor.MonitorAbstract;

/**
 * @see http://wiki.pentaho.com/display/EAI/PDI+Extension+Point+Plugins
 */

@ExtensionPoint(
  id = "JobFinishMonitor",
  extensionPointId = "JobFinish",
  description = "A job finishes"
)
public class JobFinishMonitor extends MonitorAbstract implements ExtensionPointInterface {

  @Override
  public IKettleMonitoringEvent toKettleEvent( Object o ) throws KettleException {

    if ( o == null || !( o instanceof Job ) ) {
      return null;
    }

    JobEvent jobEvent = new JobEvent( JobEvent.EventType.FINISHED ).build( (Job) o );

    getLog().logDebug( "JobFinishMonitor - " + ( (Job) o ).getName()
      + " , completed in " + ( jobEvent.getStartTimeMillis() > 0 ?
      ( ( ( jobEvent.getEndTimeMillis() - jobEvent.getStartTimeMillis() ) / 1000 ) % 60 ) : 0 ) + " seconds");

    return jobEvent;
  }
}
