/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at legal-notices/CDDLv1_0.txt
 * or http://forgerock.org/license/CDDLv1.0.html.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at legal-notices/CDDLv1_0.txt.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *      Copyright 2007-2009 Sun Microsystems, Inc.
 *      Portions Copyright 2013-2015 ForgeRock AS
 */
package org.opends.server.replication.server;

import java.net.InetAddress;
import java.util.SortedSet;
import java.util.TreeSet;

import org.opends.server.admin.Configuration;
import org.opends.server.admin.server.ConfigurationChangeListener;
import org.opends.server.admin.server.ServerManagedObject;
import org.opends.server.admin.std.meta.ReplicationServerCfgDefn.ReplicationDBImplementation;
import org.opends.server.admin.std.server.ReplicationServerCfg;
import org.opends.server.types.DN;

/**
 * This Class implements an object that can be used to instantiate
 * The ReplicationServer class for tests purpose.
 */
@SuppressWarnings("javadoc")
public class ReplServerFakeConfiguration implements ReplicationServerCfg
{
  private int port;
  private String dirName;
  private int purgeDelay;
  private int serverId;
  private int queueSize;
  private int windowSize;
  private SortedSet<String> servers;

  /*
   * Assured mode properties
   */
  /** Timeout (in milliseconds) when waiting for acknowledgments. */
  private long assuredTimeout = 1000;

  /** Group id. */
  private int groupId = 1;

  /** Threshold for status analyzers. */
  private int degradedStatusThreshold = 5000;

  /** The weight of the server. */
  private int weight = 1;

  /** The monitoring publisher period. */
  private long monitoringPeriod = 3000;
  private boolean computeChangenumber;
  
  /** The DB implementation to use for replication changelog. */
  private final ReplicationDBImplementation dbImpl;

  /**
   * Constructor without group id, assured info and weight.
   */
  public ReplServerFakeConfiguration(
      int port, String dirName, ReplicationDBImplementation dbImpl, int purgeDelay,
      int serverId, int queueSize, int windowSize, SortedSet<String> servers)
  {
    this.port    = port;
    this.dbImpl = dbImpl;
    this.dirName = dirName != null ? dirName : "changelogDb";

    if (purgeDelay == 0)
    {
      this.purgeDelay = 24*60*60;
    }
    else
    {
      this.purgeDelay = purgeDelay;
    }

    this.serverId = serverId;

    if (queueSize == 0)
    {
      this.queueSize = 10000;
    }
    else
    {
      this.queueSize = queueSize;
    }

    if (windowSize == 0)
    {
      this.windowSize = 100;
    }
    else
    {
      this.windowSize = windowSize;
    }

    this.servers = servers != null ? servers : new TreeSet<String>();
  }

  /**
   * Constructor with group id and assured info.
   */
  public ReplServerFakeConfiguration(
      int port, String dirName, ReplicationDBImplementation dbImpl, int purgeDelay,
      int serverId, int queueSize, int windowSize,
      SortedSet<String> servers, int groupId, long assuredTimeout, int degradedStatusThreshold)
  {
    this(port, dirName, dbImpl, purgeDelay, serverId, queueSize, windowSize, servers);
    this.groupId = groupId;
    this.assuredTimeout = assuredTimeout;
    this.degradedStatusThreshold = degradedStatusThreshold;
  }

  /**
   * Constructor with group id, assured info and weight.
   */
  public ReplServerFakeConfiguration(
      int port, String dirName, ReplicationDBImplementation dbImpl, int purgeDelay,
      int serverId, int queueSize, int windowSize,
      SortedSet<String> servers, int groupId, long assuredTimeout, int degradedStatusThreshold, int weight)
  {
    this(port, dirName, dbImpl, purgeDelay, serverId, queueSize, windowSize,
      servers, groupId, assuredTimeout, degradedStatusThreshold);
    this.weight = weight;
  }

  /** {@inheritDoc} */
  @Override
  public void addChangeListener(
      ConfigurationChangeListener<ReplicationServerCfg> listener)
  {
    // not supported
  }

  /** {@inheritDoc} */
  @Override
  public Class<? extends ReplicationServerCfg> configurationClass()
  {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public String getReplicationDBDirectory()
  {
    return dirName;
  }

  /** {@inheritDoc} */
  @Override
  public int getReplicationPort()
  {
    return port;
  }

  /** {@inheritDoc} */
  @Override
  public long getReplicationPurgeDelay()
  {
    return purgeDelay;
  }

  /** {@inheritDoc} */
  @Override
  public SortedSet<String> getReplicationServer()
  {
     return servers;
  }

  /** {@inheritDoc} */
  @Override
  public int getReplicationServerId()
  {
    return serverId;
  }

  /** {@inheritDoc} */
  @Override
  public InetAddress getSourceAddress() { return null; }

  /** {@inheritDoc} */
  @Override
  public int getQueueSize()
  {
    return queueSize;
  }

  /** {@inheritDoc} */
  @Override
  public int getWindowSize()
  {
    return windowSize;
  }

  /** {@inheritDoc} */
  @Override
  public void removeChangeListener(
      ConfigurationChangeListener<ReplicationServerCfg> listener)
  {
    // not supported
  }

  /** {@inheritDoc} */
  @Override
  public DN dn()
  {
    return null;
  }

  /** {@inheritDoc} */
  public ServerManagedObject<? extends Configuration> managedObject() {
    return null;
  }

  @Override
  public int getGroupId()
  {
    return groupId;
  }

  @Override
  public long getAssuredTimeout()
  {
    return assuredTimeout;
  }

  @Override
  public int getDegradedStatusThreshold()
  {
    return degradedStatusThreshold;
  }

  public void setDegradedStatusThreshold(int degradedStatusThreshold)
  {
    this.degradedStatusThreshold = degradedStatusThreshold;
  }

  @Override
  public int getWeight()
  {
    return weight;
  }

  @Override
  public long getMonitoringPeriod()
  {
    return monitoringPeriod;
  }

  public void setMonitoringPeriod(long monitoringPeriod)
  {
    this.monitoringPeriod = monitoringPeriod;
  }

  @Override
  public boolean isComputeChangeNumber()
  {
    return computeChangenumber;
  }

  public void setComputeChangeNumber(boolean computeChangenumber)
  {
    this.computeChangenumber = computeChangenumber;
  }

  @Override
  public ReplicationDBImplementation getReplicationDBImplementation()
  {
    return dbImpl;
  }
}
