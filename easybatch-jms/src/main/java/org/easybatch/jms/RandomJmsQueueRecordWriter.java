/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.easybatch.jms;

import org.easybatch.core.record.Batch;
import org.easybatch.core.record.Record;
import org.easybatch.core.writer.RecordWriter;

import javax.jms.Message;
import javax.jms.QueueSender;
import java.util.List;
import java.util.Random;

/**
 * Write records randomly to a list of Jms queues.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class RandomJmsQueueRecordWriter implements RecordWriter {

    /**
     * The total number of queues this writer operates on.
     */
    private int queuesNumber;

    /**
     * List of queues to which records should be written.
     */
    private List<QueueSender> queues;

    /**
     * The random generator.
     */
    private Random random;

    /**
     * Create a {@link RandomJmsQueueRecordWriter} instance.
     *
     * @param queues the list of queues to which records should be written
     */
    public RandomJmsQueueRecordWriter(List<QueueSender> queues) {
        this.queues = queues;
        this.queuesNumber = queues.size();
        this.random = new Random();
    }

    @Override
    public void open() throws Exception {

    }

    @Override
    public void writeRecords(Batch batch) throws Exception {
        for (Record record : batch) {
            //dispatch record randomly to one of the queues
            QueueSender queue = queues.get(random.nextInt(queuesNumber));
            queue.send((Message) record.getPayload());
        }
    }

    @Override
    public void close() throws Exception {

    }
}
